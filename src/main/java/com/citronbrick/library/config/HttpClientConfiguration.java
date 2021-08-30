package com.citronbrick.library.config;

import java.net.http.*;
import org.springframework.context.annotation.*;
import java.time.*;
import java.time.temporal.*;

@Configuration
public class HttpClientConfiguration {

	@Bean
	public HttpClient makeHttpClient() {
		return HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_1_1)
			.connectTimeout(Duration.of(2, ChronoUnit.SECONDS))
			.build();
	}

}