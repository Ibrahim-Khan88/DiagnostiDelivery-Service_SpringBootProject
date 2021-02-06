package com.diagnostic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.DropDownInput;
import com.diagnostic.repository.DropDownInputRepository;

@CrossOrigin("*")
@RestController
public class DropDownInputController {

	@Autowired
	private DropDownInputRepository droDownInputRepository;

	// http://localhost:8080/dropdowninput
	@GetMapping("/dropdowninput")
	public ResponseEntity<List<DropDownInput>> fetchInvestigationByProviderId() {

		List<DropDownInput> doctorList = droDownInputRepository.findAll();

		return ResponseEntity.ok().body(doctorList);
	}

}
