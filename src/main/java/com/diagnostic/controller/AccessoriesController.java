package com.diagnostic.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.Accessories;
import com.diagnostic.repository.AccessoriesRepository;

@CrossOrigin("*")
@RestController
public class AccessoriesController {

	@Autowired
	private AccessoriesRepository accessoriesRepository;

	// http://localhost:8080/accessories
	@GetMapping("/accessories")
	public ResponseEntity<List<Accessories>> fetch() throws ParseException {

		List<Accessories> accessoriesList = accessoriesRepository.findAll();

		return ResponseEntity.ok().body(accessoriesList);
	}

}
