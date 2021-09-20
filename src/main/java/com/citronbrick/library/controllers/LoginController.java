package com.citronbrick.library.controllers;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;
import com.citronbrick.library.services.*;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.*;
import org.springframework.http.*;

import javax.servlet.http.*;
import java.util.*; 

import lombok.*;


@RestController
public class LoginController {

	@Autowired
	private LibraryUserRepository userRepository;

	@Data
	@NoArgsConstructor
	public static class Credentials {
		String username;
		String password;
	}


	@PostMapping(value="/login",consumes={MediaType.APPLICATION_JSON_VALUE})
	public boolean login(@RequestBody Credentials credentials) {
		return userRepository.findByUsernameAndPassword(credentials.username, credentials.password) != null;
	}

}

