package com.diagnostic.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.Specimen;
import com.diagnostic.repository.SpecimenRepository;

@CrossOrigin("*")
@RestController
public class SpecimenController {

	@Autowired
	private SpecimenRepository specimenRepository;

	// http://localhost:8080/specimen
	@GetMapping("/specimen")
	public ResponseEntity<List<Specimen>> fetch() throws ParseException {

		List<Specimen> specimenList = specimenRepository.findAll();

		return ResponseEntity.ok().body(specimenList);
	}

}
