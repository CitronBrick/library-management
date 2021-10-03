package com.citronbrick.library.config;

import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;



@Configuration
public class PasswordEncoderConfiguration {

	@Bean
	public PasswordEncoder makePasswordEncoder() {
		// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return new BCryptPasswordEncoder();
	}


}
