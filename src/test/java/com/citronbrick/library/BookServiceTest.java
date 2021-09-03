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

import java.util.*;


// @DataJpaTest
@SpringBootTest
class BookServiceTest {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LibraryUserRepository libraryUserRepository;

	@Autowired
	private BookService bookService;

	private static Book around80 =  null;
	private static Book progC = null;

	private static LibraryUser federer = new LibraryUser("Roger Federer","Password");
	private static LibraryUser nadal = new LibraryUser("Rafel Nadal","Password");

	static {

	}






	@BeforeEach
	public void initRepo() {
		libraryUserRepository.save(federer);
		nadal = libraryUserRepository.save(nadal);
		around80 = bookRepository.save(new Book("Around the World in 80 days","Jules Vernes",5));
		progC = bookRepository.save(new Book("The C Programming Language","Dennis Ritchie", 1));
		around80.addBorrower(nadal);
		bookRepository.save(around80);
	}

	@AfterEach
	public void emptyRep() {
		around80.setBorrowers(new ArrayList<LibraryUser>());
		bookRepository.save(around80);
		libraryUserRepository.delete(federer);
		libraryUserRepository.delete(nadal);
		bookRepository.delete(around80);
		bookRepository.delete(progC);
	}

	
	@Test
	public void borrowBookTest() {
		Assertions.assertThat(around80.getBorrowers()).contains(nadal);
		Assertions.assertThat(bookService.borrowBook(around80,nadal)).isTrue();
		Assertions.assertThat(around80.getAvailableNbCopies()).isEqualTo(4);

		for(var i = 0 ; i < 4; i++) {
			Assertions.assertThat(bookService.borrowBook(around80,nadal)).isTrue();
		}
		Assertions.assertThat(bookService.borrowBook(around80,nadal)).isFalse();

	}

	@Test
	public void returnBookTest() {
		// nadel return his 2 books
		Assertions.assertThat(around80.getBorrowers()).contains(nadal);
		Assertions.assertThat(bookService.returnBook(around80,nadal)).isTrue();
		Assertions.assertThat(around80.getBorrowers()).doesNotContain(nadal);
		Assertions.assertThat(around80.getAvailableNbCopies()).isEqualTo(6);
		Assertions.assertThat(bookService.returnBook(around80,nadal)).isFalse();


		Assertions.assertThat(bookService.returnBook(progC,federer)).isFalse();
		Assertions.assertThat(progC.getAvailableNbCopies()).isEqualTo(1);
		
	}

	
}
