package com.citronbrick.library.config;

import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.http.*;
import lombok.*;

@NoArgsConstructor
@Component 
public class LibrarySecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/**").permitAll()
			.mvcMatchers(HttpMethod.POST,"/books/create").permitAll()
			.and().csrf().disable();
	}
	
}
