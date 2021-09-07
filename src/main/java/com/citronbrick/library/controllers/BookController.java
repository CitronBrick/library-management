package com.citronbrick.library.controllers;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import java.util.*; 


@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;


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

}

