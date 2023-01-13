package com.bootcamp01.one.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp01.one.model.Person;
import com.bootcamp01.one.repository.PersonDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	private PersonDao personDao;

	@Override
	public Flux<Person> findAll() {
		return personDao.findAll();
	}

	@Override
	public Mono<Person> findById(String id) {
		return personDao.findById(id);
	}

	@Override
	public Mono<Person> save(Person person) {
		return personDao.save(person);
	}

	@Override
	public Mono<Void> delete(Person person) {
		return personDao.delete(person);
	}

}
