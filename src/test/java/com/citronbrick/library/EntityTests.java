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


@DataJpaTest
class EntityTests {

	@Autowired
	private BookRepository bookRepository;


	

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

	/*@Test
	@Disabled
	void atleastOneBookHasBeenPreCreated() {
		Assertions.assertThat(bookRepository.count()).isGreaterThan(5);
	}*/
}
