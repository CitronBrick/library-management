package com.citronbrick.library.services;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.client.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;


import java.net.http.*;
import java.net.*;
import java.io.*;
import java.util.stream.*;
import java.util.*;
import javax.annotation.*;
import javax.json.bind.*;
import javax.json.*;
import lombok.*;

@Service
public class LibraryUsersInitializationService {

	private LibraryUserRepository userRepository;
	private RestTemplate restTemplate;
	private Jsonb jsonb;
	private Random rand = new Random();


	public LibraryUsersInitializationService(@Autowired LibraryUserRepository userRepository, @Autowired Jsonb jsonb) {
		this.userRepository = userRepository;
		// this.restTemplate = restTemplate;
		this.jsonb = jsonb;
	}


	@PostConstruct
	public void initUsers() {
		this.restTemplate = new RestTemplate(List.<HttpMessageConverter<?>>of(new JsonbHttpMessageConverter(jsonb)));
		ResponseEntity<RandomUserResults> re = this.restTemplate.getForEntity("https://randomuser.me/api/?results=20&inc=login", RandomUserResults.class);
		RandomUserResults rur = re.getBody(); 
		for(Login login : rur.results) {
			LoginDetails ld = login.login;
			var librarian = rand.nextInt(5) == 0;
			userRepository.save(new LibraryUser(ld.username, ld.password, librarian));

		}
		userRepository.save(new LibraryUser("sachin", "password"));
		userRepository.save(new LibraryUser("dravid", "password",true));
	}



	/* 
		

	*/


	@Data
	private static class RandomUserResults {
		private Login[] results;
	}

	@Data
	private static class Login {
		LoginDetails login;
	}

	@Data
	private static class LoginDetails {
		String uuid;
		String username;
		String password;
		String salt;
		String md5;
		String sha1;
		String sha256;
	}

} 