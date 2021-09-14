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
public class UserControllerTests {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserDetailsService uds;

	@Autowired
	private UserController userController;

	private MockMvc mockMvc;

	private UserDetails sachin, dravid;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();

		sachin = uds.loadUserByUsername("sachin");
		dravid = uds.loadUserByUsername("dravid");
	}

	@Test
	public void testGetAllUsers() throws Exception {
		Assertions.assertThat(sachin.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))).isFalse();
		Assertions.assertThat(dravid.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))).isTrue();

		mockMvc
			.perform(MockMvcRequestBuilders.get("/users/all"))
			.andExpect(MockMvcResultMatchers.status().isForbidden());

		mockMvc
			.perform(MockMvcRequestBuilders.get("/users/all").with(SecurityMockMvcRequestPostProcessors.user(dravid)))
			.andExpect(MockMvcResultMatchers.status().isOk());


		mockMvc
			.perform(MockMvcRequestBuilders.get("/users/all").with(SecurityMockMvcRequestPostProcessors.user(sachin)))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

}