package com.citronbrick.library;

import com.citronbrick.library.repositories.*;
import com.citronbrick.library.entities.*;
import com.citronbrick.library.services.*;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.boot.web.servlet.*;


import javax.json.bind.*;
import java.time.*;
import lombok.*;

@ServletComponentScan
@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserDetailsService uds;

	@Autowired Jsonb jsonb;

	@Data
	@AllArgsConstructor
	class Task {
		String description;
	    LocalDate deadline;
	    boolean over;


	}




	public void run(String... args) {
		Book b = new Book();
		b.setTitle("CLRS book");
		b.setAuthor("CLRS");
		bookRepository.save(b);


		Task t = new Task("Jump", LocalDate.of(2021,10,15),false);
		String res1 = jsonb.toJson(t);
		System.out.println(res1);

		System.out.println(uds);
		var dravid = uds.loadUserByUsername("dravid");
		System.out.println(dravid.getAuthorities());
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
