package com.bootcamp01.one.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Document(collection="person")
@Data
public class Person {
	@Id
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String lastName;
	@NotEmpty
	private String dni;
	@NotEmpty
	private String email;
	@NotEmpty
	private String telephone;
	
}
