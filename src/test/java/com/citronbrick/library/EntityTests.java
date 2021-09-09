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
	void createAndFindNormalUser() {
		LibraryUser user = new LibraryUser("Messi","password",false);
		LibraryUser savedUser = libraryUserRepository.save(user);

		long id = user.getId();
		Assertions.assertThat(id).isNotNull();

		LibraryUser foundUser = libraryUserRepository.getById(id);

		Assertions.assertThat(foundUser).isEqualToComparingOnlyGivenFields(user,"username","password","librarian");
	}

	@Test
	void createAndFindLibrarian() {
		LibraryUser user = new LibraryUser("Ronaldinho","password",true);
		LibraryUser savedUser = libraryUserRepository.save(user);

		long id = user.getId();
		Assertions.assertThat(id).isNotNull();
		Assertions.assertThat(savedUser.isLibrarian()).isTrue();

		LibraryUser foundUser = libraryUserRepository.getById(id);
		Assertions.assertThat(foundUser.isLibrarian()).isTrue();


		Assertions.assertThat(foundUser).isEqualToComparingOnlyGivenFields(user,"username","password","librarian");
	}



	
}
