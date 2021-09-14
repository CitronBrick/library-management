package com.citronbrick.library.repositories;

import com.citronbrick.library.entities.*;
import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;




@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {


	// void deleteLibraryUserInBooksBorrowers()

	LibraryUser findByUsername(String username);

	
	
}