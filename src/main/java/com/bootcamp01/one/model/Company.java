package com.bootcamp01.one.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Document(collection="company")
public class Company {
	@Id
	private String id;
	@NotEmpty
	private String businessName;
	@NotEmpty
	private String ruc;
	@NotEmpty
	private String email;
	@NotEmpty
	private String telephone;
	@NotEmpty
	@NonNull
	private List<SignerHolders> holders;
	
	private List<SignerHolders> signer;
	
	public Company() {
		
	}
	
}
