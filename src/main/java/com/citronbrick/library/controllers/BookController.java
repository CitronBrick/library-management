package com.citronbrick.library.controllers;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;
import com.citronbrick.library.services.*;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.*;
import org.springframework.http.*;

import javax.servlet.http.*;
import java.util.*; 


@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookService bookService;


	@GetMapping("/all")
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public Book getAllBooks(@PathVariable("id") Long bookId) {
		// getById returns a child proxy Book object which does not play well with serialization for Spring web MVC
		return  bookRepository.findById(bookId).get();
	}  



	// using reaons in @ResponseStatus causes sendError to be called
	// resulting in empty response in test
	@PostMapping("/create")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Book makeBook(@RequestBody Book b ) {
		return bookRepository.save(b);
	}

	@PutMapping("{bookId}/borrow/{userId}")
	public boolean borrowBook(@PathVariable("bookId") long bookId, @PathVariable("userId") long userId, @AuthenticationPrincipal(errorOnInvalidType=true) UserDetails user) {
		return bookService.borrowBook(bookId,userId);
	}

}

