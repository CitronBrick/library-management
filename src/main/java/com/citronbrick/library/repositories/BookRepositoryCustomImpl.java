package com.citronbrick.library.repositories;

import com.citronbrick.library.entities.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.jpa.repository.*;
import javax.persistence.*;
import java.util.*;

public class BookRepositoryCustomImpl implements BookRepositoryCustom  {

	@Autowired
	private EntityManager em;

	public List<Book> findByBorrowers(LibraryUser lu) {
		return em.createQuery("Select b from Book b, LibraryUser lu where b in :lu.borrowedBooks").setParameter("lu",lu).getResultList();
	}

}