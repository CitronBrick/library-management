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


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private LibraryUserRepository userRepository;


	@GetMapping("/all")
	public List<LibraryUser> getAllUsers(HttpServletRequest hsr) {
		System.out.println("je suis apppeeeeeeee");
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public LibraryUser getAllBooks(@PathVariable("id") Long userId) {
		return  userRepository.findById(userId).get();
	}  


	@PostMapping("/create")
	@ResponseStatus(code=HttpStatus.CREATED)
	public LibraryUser makeUser(@RequestBody LibraryUser lu ) {
		return userRepository.save(lu);
	}


}

