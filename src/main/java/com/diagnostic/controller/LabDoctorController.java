package com.diagnostic.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.LabDoctor;
import com.diagnostic.repository.LabDoctorRepository;
import com.diagnostic.service.LabDoctorService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
public class LabDoctorController {

	@Autowired
	private LabDoctorRepository labDoctorRepository;

	@Autowired
	private LabDoctorService labDoctorService;

	// http://localhost:8080/labdoctor/1
	@GetMapping("/labdoctor/{providerId}")
	public ResponseEntity<List<LabDoctor>> fetchInvestigationByProviderId(
			@PathVariable(value = "providerId") int providerId) {

		List<LabDoctor> labDoctorlist = labDoctorRepository.findByProviderId(providerId);

		return ResponseEntity.ok().body(labDoctorlist);
	}

	// http://localhost:8080/labdoctor/537
	@PostMapping("/labdoctor/{investigationRequestId}")
	ResponseEntity<?> createInvestigationRequest(
			@PathVariable(value = "investigationRequestId") int investigationRequestId,
			@RequestParam("labDoctorName") String labDoctorName)
			throws JsonParseException, JsonMappingException, IOException {

		boolean result = labDoctorService.assignLabDoctor(labDoctorName, investigationRequestId);

		if (result) {
			return ResponseEntity.ok().body("successfully updated");
		} else {
			return ResponseEntity.badRequest().body("Fail to update");
		}

		// 1,2,3,4,5,6
		// positive,negative,positive,negative,positive,negative

	}

}
