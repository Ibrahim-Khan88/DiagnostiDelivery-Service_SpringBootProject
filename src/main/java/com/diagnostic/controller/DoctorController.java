package com.diagnostic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.Doctor;
import com.diagnostic.repository.DoctorRepository;

@CrossOrigin("*")
@RestController
public class DoctorController {

	@Autowired
	private DoctorRepository doctorRepository;

	// http://localhost:8080/doctor/1
	@GetMapping("/doctor/{providerId}")
	public ResponseEntity<List<Doctor>> fetchInvestigationByProviderId(
			@PathVariable(value = "providerId") int providerId) {

		List<Doctor> doctorList = doctorRepository.findByProviderId(providerId);

		return ResponseEntity.ok().body(doctorList);
	}

}
