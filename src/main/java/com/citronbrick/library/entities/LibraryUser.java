package com.citronbrick.library.entities;

import lombok.*;
import javax.persistence.*;
import javax.persistence.Transient;
import java.io.*;
import java.util.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.crypto.password.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

@Data
@NoArgsConstructor
@Entity
public class LibraryUser implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(unique=true)
	private String username;
	private String password;

	@ManyToMany(mappedBy="borrowers",fetch=FetchType.EAGER)
	private List<Book> borrowedBooks = new ArrayList<>();

	private boolean librarian = false;


	@Transient
	private PasswordEncoder encoder;

	@Transient
	private ApplicationContext context;


	public LibraryUser(String username, String password, boolean librarian) {
		this.username = username;
		this.password = password;
		this.librarian = librarian;
	}

	public LibraryUser(String username, String password) {
		this(username,password,false);
	}


	public Collection<? extends GrantedAuthority> getAuthorities() {
		var res = new ArrayList<GrantedAuthority>();
		res.add(new SimpleGrantedAuthority("ROLE_USER"));
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
