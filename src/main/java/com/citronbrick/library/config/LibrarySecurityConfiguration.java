package com.citronbrick.library.config;

import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.http.*;
import lombok.*;

@NoArgsConstructor
@EnableWebSecurity 
public class LibrarySecurityConfiguration extends WebSecurityConfigurerAdapter {
		
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers(HttpMethod.PUT,"/**").hasRole("LIBRARIAN")
			.mvcMatchers(HttpMethod.POST,"/login").permitAll()
			.mvcMatchers(HttpMethod.POST,"/books/create").permitAll()
			.mvcMatchers(HttpMethod.GET,"/books/all").permitAll()
			.mvcMatchers(HttpMethod.POST,"/users/**").hasRole("LIBRARIAN")
			.mvcMatchers(HttpMethod.GET,"/users/**").hasRole("LIBRARIAN")
			.mvcMatchers(HttpMethod.GET,"/**").permitAll()
			.anyRequest().authenticated()
			.and().csrf().disable();
	}
	
}
