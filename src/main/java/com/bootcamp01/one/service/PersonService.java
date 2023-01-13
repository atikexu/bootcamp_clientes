package com.bootcamp01.one.service;

import com.bootcamp01.one.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
	
	public Flux<Person> findAll();
		
	public Mono<Person> findById(String id);
	
	public Mono<Person> save(Person person);
	
	public Mono<Void> delete(Person person); 
	
}
