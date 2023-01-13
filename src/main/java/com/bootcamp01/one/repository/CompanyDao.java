package com.bootcamp01.one.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp01.one.model.Company;

public interface CompanyDao extends ReactiveMongoRepository<Company, String>{

}
