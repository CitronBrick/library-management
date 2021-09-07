package com.citronbrick.library;

import org.junit.jupiter.api.Test;
// import org.junit.runner.*;
// import org.junit.*;
import org.assertj.core.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.junit4.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;


// @RunWith(SpringRunner.class)
@SpringBootTest
// @AutoConfigureTestDatabase
class LibraryManagementApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LibraryUserRepository userRepository;


	@Test
	void contextLoads() {
	}

	@Test
	void atLeastOneBookHasBeenPreCreated() {
		Assertions.assertThat(bookRepository.count()).isGreaterThan(5);
	}

	@Test
	void atLeast20UsersHaveBeenPreCreated() {
		Assertions.assertThat(userRepository.count()).isGreaterThanOrEqualTo(20);
	}

	
}
