package com.bootcamp01.one.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp01.one.model.Company;
import com.bootcamp01.one.repository.CompanyDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyDao companyDao;

	@Override
	public Flux<Company> findAll() {
		return companyDao.findAll();
	}

	@Override
	public Mono<Company> findById(String id) {
		return companyDao.findById(id);
	}

	@Override
	public Mono<Company> save(Company person) {
		return companyDao.save(person);
	}

	@Override
	public Mono<Void> delete(Company person) {
		return companyDao.delete(person);
	}

}
