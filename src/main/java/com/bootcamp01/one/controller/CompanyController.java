package com.bootcamp01.one.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.bootcamp01.one.model.Company;
import com.bootcamp01.one.service.CompanyService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Company>>> listPCompany(){
		return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(companyService.findAll())
				);
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> saveCompany(@Valid @RequestBody Mono<Company> company){
		Map<String, Object> response = new HashMap<>();

		return company.flatMap(p -> {
			return companyService.save(p).map(c -> {
				
				response.put("company", c);
				response.put("mensaje", "Client saved successfully");
				return ResponseEntity
						.created(URI.create("/clients/company/register/".concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(response);
			});
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "the field "+fieldError.getField() + " "+ fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						response.put("errors", list);
						response.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(response));
					});
		});
	}
	
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<Company>> updateCompany(@RequestBody Company company, @PathVariable String id){
		return companyService.findById(id).flatMap(p -> {
			p.setRuc(company.getRuc());
			p.setEmail(company.getEmail());
			p.setBusinessName(company.getBusinessName());
			p.setTelephone(company.getTelephone());
			p.setSigner(company.getSigner());
			p.setHolders(company.getHolders());
			return companyService.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/company/update/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> deleteCompany(@PathVariable String id){
		return companyService.findById(id).flatMap(p -> {
			return companyService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
