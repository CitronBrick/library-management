package com.citronbrick.library.config;

import javax.json.bind.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class JsonConfiguration {

	@Bean
	public Jsonb jsonb() {
		return JsonbBuilder.create();
	}

	/*@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(List.of<>)
	}*/
}