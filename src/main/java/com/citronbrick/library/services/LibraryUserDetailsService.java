package com.citronbrick.library.services;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.security.core.userdetails.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;



@Service
public class LibraryUserDetailsService implements UserDetailsService {

	private LibraryUserRepository userRepository;

	public LibraryUserDetailsService(@Autowired LibraryUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + " not found");
		} else {
			return user;
		}
	}
}
