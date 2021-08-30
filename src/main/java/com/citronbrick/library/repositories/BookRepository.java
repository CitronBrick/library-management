package com.citronbrick.library.repositories;

import com.citronbrick.library.entities.*;
import org.springframework.stereotype.*;
import org.springframework.data.jpa.repository.*;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

}