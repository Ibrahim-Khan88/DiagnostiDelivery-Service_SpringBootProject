package com.diagnostic.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.Patient;
import com.diagnostic.repository.PatientRepository;
import com.diagnostic.service.PatientService;

@CrossOrigin("*")
@RestController
public class PatientController {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private PatientService patientService;

	// http://localhost:8080/patient/save
	@PostMapping("/patient/save")
	ResponseEntity<?> CreateCategory(@RequestBody Patient patient) {

		Patient p = patientRepository.save(patient);

		if (p != null) {
			return ResponseEntity.ok().body("successfully created");
		}

		return ResponseEntity.badRequest().body("Fail to create");

	}

	// http://localhost:8080/patient/b000010
	@PostMapping("/patient/{billNumber}")
	ResponseEntity<?> UpdatePaymentAmount(@PathVariable(value = "billNumber") String billNumber,
			@RequestParam("amount") int amount) {

		boolean result = patientService.updatePaymentAmount(amount, billNumber);
//		System.out.println("billNumber = " + billNumber + " " + amount); @RequestBody int amount,
//		boolean result = true; 

		if (result) {
			return ResponseEntity.ok().body("successfully Updated");
		}

		return ResponseEntity.badRequest().body("Fail to update");

	}

	// http://localhost:8080/patient/163
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<Patient> givebook(@PathVariable(value = "patientId") int patientId) throws ParseException {

		Optional<Patient> patient = patientRepository.findById(patientId);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E dd,MMM,yy");

		String date = simpleDateFormat.format("2020-09-19 15:41:38.0");

		return ResponseEntity.ok().body(patient.get());
	}

}
