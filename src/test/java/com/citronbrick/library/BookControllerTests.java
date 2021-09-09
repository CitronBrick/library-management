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
import org.springframework.security.test.web.servlet.request.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;
import java.io.*;
import javax.json.*;
import javax.json.bind.*;


@SpringBootTest
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {

	@Autowired
	private WebApplicationContext context;


	@Autowired
	private Jsonb jsonb;

	@Autowired
	private UserDetailsService uds;

	// @Autowired MockHttpServletResponse response;


	private MockMvc mockMvc;
	private LibraryUser sachin;
	private LibraryUser dravid;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();
		sachin = (LibraryUser)uds.loadUserByUsername("sachin");
		dravid = (LibraryUser)uds.loadUserByUsername("dravid");
		System.out.println(dravid);
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
	public void testBorrowBook() throws Exception {
		var userId = sachin.getId();
		Assertions.assertThat(sachin.isLibrarian()).isFalse();
		System.out.println(sachin.getAuthorities());
		sachin.setLibrarian(true);
		System.out.println(dravid);
		try {

			mockMvc.perform(
				MockMvcRequestBuilders
					.put("/books/2/borrow/"+userId)
					.with(SecurityMockMvcRequestPostProcessors.user(sachin))
			).andExpect(
				MockMvcResultMatchers.status().isForbidden()
			);
		} catch(UnsupportedOperationException uoe) {
			uoe.printStackTrace();
		}
	}	

	@Test
	@Disabled
	public void testSerial() {
		var str = "[{\"key\":\"key1\", \"value\":\"value1\"}, {\"key\":\"key2\", \"value\":\"value2\"}, {\"key\":\"keyN\", \"value\":\"valueN\"}]";
		var reader = Json.createReader(new StringReader(str));
		var jarr = reader.readArray();
		var res = new HashMap<String,String>();
		for(JsonValue jv : jarr) {
			var jo = (JsonObject)jv;
			res.put(jo.getString("key"), jo.getString("value") );
		}
		System.out.println(res);
	}

}