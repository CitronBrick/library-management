package com.citronbrick.library;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;
import com.citronbrick.library.controllers.*;

import org.junit.jupiter.api.*;
import org.hamcrest.*;
import org.assertj.core.api.*;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.web.context.*;
import org.springframework.mock.web.*;

import java.util.*;
import javax.json.*;
import javax.json.bind.*;


@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private Jsonb jsonb;

	// @Autowired MockHttpServletResponse response;


	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();
	}


	@Test
	public void testBookCreation() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders
				.post("/books/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					jsonb.toJson(new Book("KR","K & R",5,8))
				)
		).andExpect(
			MockMvcResultMatchers.status().isCreated()
		).andExpect(
			MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.author", Matchers.is("K & R"))
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.title", Matchers.is("KR"))
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.availableNbCopies", Matchers.is(5))
		).andExpect(
			MockMvcResultMatchers.jsonPath("$.totalNbCopies", Matchers.is(8))
		);
	}


	@Test
	public void testGetAllBooks() throws Exception {
		mockMvc.perform(
			MockMvcRequestBuilders.get("books/all")
		).andExpect(
			MockMvcRequestBuilders.status().isOk()
		);
	}

	

}