package com.citronbrick.library;


import com.citronbrick.library.controllers.*;
import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

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
public class LoginControllerTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserDetailsService uds;

	@Autowired
	private UserController userController;

	@Autowired
	private LibraryUserRepository userRepository;

	private MockMvc mockMvc;

	private LibraryUser sachin, dravid;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();

		/*sachin = (LibraryUser)uds.loadUserByUsername("sachin");
		dravid = (LibraryUser)uds.loadUserByUsername("dravid");*/
		sachin = userRepository.findByUsername("sachin");
		dravid = userRepository.findByUsername("dravid");
	}

	@Test 
	public void testLoginPage() throws Exception {
		mockMvc
			.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testNormalLogin() throws Exception {

		JsonObject sachin = Json.createObjectBuilder()
			.add("username","sachin")
			.add("password","password")
			.build();

		mockMvc
			.perform(MockMvcRequestBuilders
				.post("/login")
				.content(sachin.toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());

	}


}