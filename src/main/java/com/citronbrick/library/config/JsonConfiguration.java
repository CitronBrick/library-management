package com.citronbrick.library.config;

import javax.json.bind.*;
import org.springframework.context.annotation.*;

@Configuration
public class JsonConfiguration {



	@Bean
	public Jsonb jsonb() {
		return JsonbBuilder.create();
	}
}