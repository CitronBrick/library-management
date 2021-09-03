package com.citronbrick.library;

import org.junit.jupiter.api.*;
// import org.junit.runner.*;
// import org.junit.*;
import org.assertj.core.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.junit4.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;
import com.citronbrick.library.services.*;


@DataJpaTest
class EntityTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LibraryUserRepository libraryUserRepository;

	
	

	@Test
	void createAndFindBook() {
		Book b = new Book();
		b.setTitle("Pride and Prejudice");
		b.setAuthor("Austen");
		Book savedBook = bookRepository.save(b);
		/*Assert.assertNotNull(savedBook.getId());
		Assert.assertEquals("Pride and Prejudice", savedBook.getName());
		Assert.assertEquals("Austen", savedBook.getAuthor());*/
		Long id = savedBook.getId();
		Assertions.assertThat(id).isNotNull();

		Book foundBook = bookRepository.getById(id);

		Assertions.assertThat(foundBook).isEqualToComparingOnlyGivenFields(b,"title","author");
	}

	@Test
	void findByBorrower() {
		LibraryUser lu = new LibraryUser("Sachin","password");
		Book b = new Book("Oliver Twist","Mark Twain");
		b.addBorrower(lu);
		lu.addBook(b);
		lu = libraryUserRepository.save(lu);
		bookRepository.save(b);

		var sachinBorrowedList = bookRepository.findByBorrowers(lu); 
		System.out.println(sachinBorrowedList);
		/*Assertions.assertThat(sachinBorrowedList).isNotEmpty();*/
	}

	/*@Test
	@Disabled
	void atleastOneBookHasBeenPreCreated() {
		Assertions.assertThat(bookRepository.count()).isGreaterThan(5);
	}*/
}
