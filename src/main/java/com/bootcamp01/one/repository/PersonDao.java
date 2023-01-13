package com.bootcamp01.one.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp01.one.model.Person;

public interface PersonDao extends ReactiveMongoRepository<Person, String>{

}
