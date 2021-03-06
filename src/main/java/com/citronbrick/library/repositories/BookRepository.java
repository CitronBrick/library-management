package com.citronbrick.library.repositories;

import com.citronbrick.library.entities.*;
import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{

	public Book findByTitle(String title);	

	public List<Book> findByAuthor(String author);

	// public List<Book> findByBorrowers(LibraryUser lu);

}