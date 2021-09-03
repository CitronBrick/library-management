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
public class LibraryUserService {

	private BookRepository bookRepository;
	private LibraryUserRepository libraryUserRepository;


	public LibraryUserService(@Autowired BookRepository bookRepository, @Autowired LibraryUserRepository libraryUserRepository) {
		this.bookRepository = bookRepository;
		this.libraryUserRepository = libraryUserRepository;
	}

	public boolean deleteLibraryUser(LibraryUser lu) {
		return true;
	}


}

