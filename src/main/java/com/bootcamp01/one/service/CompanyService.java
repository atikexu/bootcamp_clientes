package com.bootcamp01.one.service;

import com.bootcamp01.one.model.Company;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CompanyService {
	
	public Flux<Company> findAll();
		
	public Mono<Company> findById(String id);
	
	public Mono<Company> save(Company company);
	
	public Mono<Void> delete(Company company); 
	
}
