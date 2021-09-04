package com.citronbrick.library;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;
import com.citronbrick.library.controllers.*;

import org.junit.jupiter.api.*;
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

import java.util.*;
import javax.json.*;


@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {

	@Autowired
	private WebApplicationContext context;

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
					Json.createObjectBuilder()
						.add("title","The Art of Computer Programming")
						.add("author", "Donald Knuth")
						.add("availableNbCopies",5)
						.add("totalNbCopies",8)
						.build().toString()

				)
		).andExpect(
			MockMvcResultMatchers.status().isCreated()
		);
	}


}