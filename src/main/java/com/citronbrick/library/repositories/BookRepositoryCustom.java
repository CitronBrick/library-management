package com.citronbrick.library.repositories;

import com.citronbrick.library.entities.*;
import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface BookRepositoryCustom  {

	public List<Book> findByBorrowers(LibraryUser lu);

}