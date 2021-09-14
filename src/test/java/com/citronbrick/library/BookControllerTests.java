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
import org.springframework.security.test.web.servlet.setup.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;
import org.springframework.web.context.*;
import org.springframework.mock.web.*;
import org.springframework.security.test.web.servlet.request.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.*;


import java.util.*;
import java.io.*;
import java.net.http.*;
import javax.json.*;
import javax.json.bind.*;


@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {

	@Autowired
	private WebApplicationContext context;

	// @Autowired
	// private BookRepository bookRepository;


	@Autowired
	private Jsonb jsonb;

	@Autowired
	private UserDetailsService uds;


	// @Autowired MockHttpServletResponse response;


	private MockMvc mockMvc;
	private LibraryUser sachin;
	private LibraryUser dravid;
	private LibraryUser gilbert;
	// private Book clrs;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();
		sachin = (LibraryUser)uds.loadUserByUsername("sachin");
		gilbert = (LibraryUser)uds.loadUserByUsername("gilbert");
		dravid = (LibraryUser)uds.loadUserByUsername("dravid");
		// clrs = bookRepository.findByTitle("CLRS");
		// System.out.println(clrs);
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
			MockMvcRequestBuilders.get("/books/all")
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		);
	}


	@Test
	public void borrowBookwithLibrarian() throws Exception {
		var userId = dravid.getId();
		Assertions.assertThat(dravid.isLibrarian()).isTrue();
		// System.out.println(dravid.getAuthorities());
		try {

			mockMvc.perform(
				MockMvcRequestBuilders
					.put("/books/2/borrow/"+userId)
					.with(SecurityMockMvcRequestPostProcessors.user(dravid))
			).andExpect(
				MockMvcResultMatchers.status().isOk()
			);
		} catch(UnsupportedOperationException uoe) {
			uoe.printStackTrace();
		}
	}

	@Test
	public void borrowBookwithNormalUser() throws Exception {
		try {
			var userId = sachin.getId();
			Assertions.assertThat(sachin.isLibrarian()).isFalse();
			Assertions.assertThat(sachin.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))).isFalse();

				mockMvc.perform(
					MockMvcRequestBuilders
						.put("/books/2/borrow/"+userId)
						// .put("/books/"+clrs.getId()+"/borrow/"+userId)
						.with(SecurityMockMvcRequestPostProcessors.user(sachin))
				).andExpect(
					MockMvcResultMatchers.status().isForbidden()
				);
			
		} catch(HttpConnectTimeoutException hcte) {
			hcte.printStackTrace();
		}
	}	

	@Test
	public void returnBookwithLibrarian() throws Exception {
		var userId = dravid.getId();
		Assertions.assertThat(dravid.isLibrarian()).isTrue();
		mockMvc.perform(
			MockMvcRequestBuilders
				.put("/books/2/return/"+userId)
				.with(SecurityMockMvcRequestPostProcessors.user(dravid))
		).andExpect(
			MockMvcResultMatchers.status().isOk()
		);
	}

	@Test
	public void returnBookwithNormalUser() throws Exception {
		var userId = sachin.getId();
		Assertions.assertThat(sachin.isLibrarian()).isFalse();
		mockMvc.perform(
			MockMvcRequestBuilders
				.put("/books/2/return/"+userId)
				.with(SecurityMockMvcRequestPostProcessors.user(sachin))
		).andExpect(
			MockMvcResultMatchers.status().isForbidden()
		);
	}

	

}