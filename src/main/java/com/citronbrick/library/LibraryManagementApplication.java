package com.citronbrick.library;

import com.citronbrick.library.repositories.*;
import com.citronbrick.library.entities.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.beans.factory.annotation.*;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;


	public void run(String... args) {
		Book b = new Book();
		b.setTitle("CLRS book");
		b.setAuthor("CLRS");
		bookRepository.save(b);

	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
