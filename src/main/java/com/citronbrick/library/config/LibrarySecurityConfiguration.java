package com.citronbrick.library.config;

import com.citronbrick.library.services.*;
import com.citronbrick.library.repositories.*;

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import lombok.*;

@NoArgsConstructor
@Configuration
@EnableWebSecurity 
public class LibrarySecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService uds;

	@Override
	public UserDetailsService userDetailsService() {
		return uds;
	}


	@Override
	public void configure(AuthenticationManagerBuilder amb) throws Exception {
		amb.userDetailsService(uds);
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/*");
	}
		
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.httpBasic()
			.and()
				.authorizeRequests()
				// .antMatchers(HttpMethod.GET,"/toto/index.html").permitAll()
				// .mvcMatchers(HttpMethod.GET,"*.css").permitAll()
				// .mvcMatchers(HttpMethod.GET,"*.js").permitAll()
				// .mvcMatchers(HttpMethod.GET,"/toto/**").permitAll()
				.mvcMatchers(HttpMethod.POST,"/login").permitAll()
				.mvcMatchers(HttpMethod.PUT,"/**").hasRole("LIBRARIAN")
				.mvcMatchers(HttpMethod.POST,"/books/create").permitAll()
				.mvcMatchers(HttpMethod.POST,"/books/create").permitAll()
				.mvcMatchers(HttpMethod.GET,"/books/all").permitAll()
				.mvcMatchers(HttpMethod.POST,"/users/**").hasRole("LIBRARIAN")
				.mvcMatchers(HttpMethod.GET,"/users/**").hasRole("LIBRARIAN")
			.and().authorizeRequests().anyRequest().authenticated();
			/*.mvcMatchers(HttpMethod.PUT,"/**").hasRole("LIBRARIAN")
			
			.mvcMatchers(HttpMethod.POST,"/books/create").permitAll()
			.mvcMatchers(HttpMethod.GET,"/books/all").permitAll()
			.mvcMatchers(HttpMethod.POST,"/users/**").hasRole("LIBRARIAN")
			.mvcMatchers(HttpMethod.GET,"/users/**").hasRole("LIBRARIAN")
			.mvcMatchers(HttpMethod.GET,"/**").permitAll()
			.anyRequest().authenticated()
			.and().httpBasic();*/
			// .and().csrf().disable();
	}


	


	
}
