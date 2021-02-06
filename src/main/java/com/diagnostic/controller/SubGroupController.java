package com.diagnostic.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.SubGroup;
import com.diagnostic.repository.SubGroupRepository;

@CrossOrigin("*")
@RestController
public class SubGroupController {

	@Autowired
	private SubGroupRepository subGroupRepository;

	// http://localhost:8080/subGroup
	@GetMapping("/subGroup")
	public ResponseEntity<List<SubGroup>> fetch() throws ParseException {

		List<SubGroup> subGroupList = subGroupRepository.findAll();

		return ResponseEntity.ok().body(subGroupList);
	}

}
