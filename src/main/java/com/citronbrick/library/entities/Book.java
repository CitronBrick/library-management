package com.citronbrick.library.entities;

import lombok.*;
import javax.persistence.*;
import java.io.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title;
	private String author;
	private int availableNbCopies = 1;
	private int totalNbCopies = 1;

	@ManyToMany
	private List<LibraryUser> borrowers = new ArrayList<>();


	public Book(String title, String author) {
		this(title, author, 1, 1);
	}

	public Book(String title, String author, int totalNbCopies) {
		this(title, author, totalNbCopies, totalNbCopies);
	}


	public Book(String title, String author, int availableNbCopies, int totalNbCopies ) {
		this.title = title;
		this.author = author;
		this.availableNbCopies = availableNbCopies;
		this.totalNbCopies = totalNbCopies;
	}

	public boolean addBorrower(LibraryUser lu) {
		return borrowers.add(lu);
	}

	public boolean removeBorrower(LibraryUser lu) {
		return borrowers.remove(lu);
	}
	

}
