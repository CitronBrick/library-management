package com.citronbrick.library.controllers;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

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

}

