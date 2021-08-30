package com.citronbrick.library.entities;

import lombok.*;
import javax.persistence.*;
import java.io.*;

@Data
@Entity
public class Book implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String title;
	private String author;


	

}
