package com.citronbrick.library.services;

import com.citronbrick.library.entities.*;
import com.citronbrick.library.repositories.*;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import java.net.http.*;
import java.net.*;
import java.io.*;
import java.util.stream.*;
import java.util.*;
import javax.annotation.*;
import javax.json.bind.*;
import javax.json.*;
import lombok.*;

@Service
public class BooksInitializationService {

	private BookRepository bookRepository;
	private HttpClient httpClient;
	private Jsonb jsonb;


	public BooksInitializationService(@Autowired BookRepository bookRepository, @Autowired HttpClient httpClient, @Autowired Jsonb jsonb) {
		this.bookRepository = bookRepository;
		this.httpClient = httpClient;
		this.jsonb = jsonb;
	}

	@PostConstruct
	public void saveBooks() {
		List<Book> bookList = fetchBooks();
		bookRepository.saveAll(bookList);
	}

	private List<Book> fetchBooks() {
		List<Book> bookList = new ArrayList<Book>();
		try {
			HttpRequest req = HttpRequest.newBuilder()
				.uri(URI.create("https://fakerestapi.azurewebsites.net/api/v1/Authors")).build();
			HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
			String body = res.body();

			// JsonArrayBuilder jab = Json.createArrayBuilder();
			JsonArray ja = jsonb.fromJson(body, JsonArray.class);

			for(JsonValue jv : ja) {
				JsonObject jo = (JsonObject)jv;
				String firstName = jo.getString("firstName");
				String lastName = jo.getString("lastName");
				int bookId = jo.getInt("idBook");
				bookList.add(fetchBook(bookId, firstName, lastName));
				
			}
		} catch(IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {
			return bookList;
		}
	}

	private Book fetchBook(int bookId, String firstName, String lastName) throws IOException, InterruptedException {
		HttpRequest req = HttpRequest.newBuilder()
			.uri(URI.create("https://fakerestapi.azurewebsites.net/api/v1/Books/"+bookId)).build();
		String body = httpClient.send(req, HttpResponse.BodyHandlers.ofString()).body();
		JsonObject jo = jsonb.fromJson(body, JsonObject.class);

		String title = Stream.of(jo.getString("description").split(" ")).limit(2).reduce(String::concat).get();

		Book b = new Book();
		b.setTitle(title);
		b.setAuthor(firstName + " " + lastName);
		return b;

	}

}

