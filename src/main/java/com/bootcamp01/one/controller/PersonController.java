package com.bootcamp01.one.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bootcamp01.one.model.Person;
import com.bootcamp01.one.service.PersonService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients/person")
public class PersonController {
	@Autowired
	private PersonService personService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping
	public Mono<ResponseEntity<Flux<Person>>> listPerson(){
		logger.info("Run process /listPerson");
		return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(personService.findAll())
				);
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> savePerson(@Valid @RequestBody Mono<Person> person){
		logger.info("Run process /savePerson");
		Map<String, Object> response = new HashMap<>();

		return person.flatMap(p -> {
			return personService.save(p).map(c -> {
				response.put("person", c);
				response.put("mensaje", "Client saved successfully");
				return ResponseEntity
						.created(URI.create("/clients/person/register/".concat(c.getId())))
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
	public Mono<ResponseEntity<Person>> updatePerson(@RequestBody Person person, @PathVariable String id){
		logger.info("Run process /updatePerson");
		return personService.findById(id).flatMap(p -> {
			p.setDni(person.getDni());
			p.setEmail(person.getEmail());
			p.setLastName(person.getLastName());
			p.setName(person.getName());
			p.setTelephone(person.getTelephone());
			return personService.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/clients/update/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
			.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> deletePerson(@PathVariable String id){
		logger.info("Run process /deletePerson");
		return personService.findById(id).flatMap(p -> {
			return personService.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
