package com.citronbrick.library.services;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import java.net.http.*;
import java.net.*;
import java.io.*;
import java.util.stream.*;
import java.util.*;
import javax.annotation.*;
import javax.json.bind.*;
import javax.json.*;
import lombok.*;

@Service
public class BookService {

	private BookRepository bookRepository;
	private LibraryUserRepository libraryUserRepository;


	public BookService(@Autowired BookRepository bookRepository, @Autowired LibraryUserRepository libraryUserRepository) {
		this.bookRepository = bookRepository;
		this.libraryUserRepository = libraryUserRepository;
	}

	public boolean borrowBook(Book b, LibraryUser lu) {
		var n = b.getAvailableNbCopies(); 
		if(n > 0) {
			b.setAvailableNbCopies(Math.max(0,n-1));
			b.addBorrower(lu);
			bookRepository.save(b);
			return true;
		}
		return false;
	}

	public boolean returnBook(Book b, LibraryUser lu) {
		if(b.getBorrowers().contains(lu)) {
			var nbCopies = b.getAvailableNbCopies();
			b.setAvailableNbCopies(nbCopies+1);
			b.removeBorrower(lu);
			bookRepository.save(b);
			return true;
		}
		return false;
	}


}

