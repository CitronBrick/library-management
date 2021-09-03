package com.citronbrick.library.entities;

import lombok.*;
import javax.persistence.*;
import java.io.*;
import java.util.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

@Data
@NoArgsConstructor
@Entity
public class LibraryUser implements Serializable, UserDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(unique=true)
	private String username;
	private String password;
	private String author;

	@ManyToMany(mappedBy="borrowers")
	private List<Book> borrowedBooks = new ArrayList<>();

	private boolean librarian = false;


	public LibraryUser(String username, String password, boolean librarian) {
		this.username = username;
		this.password = password;
		this.librarian = librarian;
	}

	public LibraryUser(String username, String password) {
		this(username,password,false);
	}


	public Collection<? extends GrantedAuthority> getAuthorities() {
		var res = List.<GrantedAuthority>of(new SimpleGrantedAuthority("ROLE_USER"));
		if(librarian) {
			res.add(new SimpleGrantedAuthority("ROLE_LIBRARIAN"));
		}
		return res;
	}

	public boolean addBook(Book b) {
		return borrowedBooks.add(b);
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean 	isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	

}
