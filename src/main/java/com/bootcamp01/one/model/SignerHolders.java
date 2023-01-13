package com.bootcamp01.one.model;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SignerHolders {
	@NotEmpty
	@NonNull
	private String name;
	@NotEmpty
	@NonNull
	private String lastName;
	@NotEmpty
	@NonNull
	private String dni;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public SignerHolders () {
		
	}
}
