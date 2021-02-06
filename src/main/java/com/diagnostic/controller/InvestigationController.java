package com.diagnostic.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.FormatResponce;
import com.diagnostic.model.Investigation;
import com.diagnostic.repository.InvestigationRepository;
import com.diagnostic.service.InvestigationService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
public class InvestigationController {

	@Autowired
	private InvestigationService investigationService;

	@Autowired
	private InvestigationRepository investigationRepository;

	// http://localhost:8080/createinvestigation/1/1/1/1
	@PostMapping("/createinvestigation/{providerId}/{departmentId}/{subGroupId}/{specimenId}")
	ResponseEntity<?> createInvestigation(@RequestBody Investigation investigation,
			@PathVariable(value = "departmentId") int departmentId, @PathVariable(value = "providerId") int providerId,
			@PathVariable(value = "subGroupId") int subGroupId, @PathVariable(value = "specimenId") int specimenId)
			throws JsonParseException, JsonMappingException, IOException {

		int resultInvestigation = investigationService.createInvestigation(investigation, departmentId, providerId,
				subGroupId, specimenId);

		if (resultInvestigation == 1) {
			return ResponseEntity.ok().body("successfully created");
		} else if (resultInvestigation == 2) {
			return ResponseEntity.badRequest().body("This investigation name is already exists");
		} else {
			return ResponseEntity.badRequest().body("Fail to create");
		}
	}

	// http://localhost:8080/investigation/13/1
	@PostMapping("/investigation/{providerInvestigationId}/{categoryId}")
	ResponseEntity<?> createInvestigationProviderInvestigationId(
			@PathVariable(value = "providerInvestigationId") int providerInvestigationId,
			@PathVariable(value = "categoryId") int categoryId, @RequestBody Investigation investigation)
			throws JsonParseException, JsonMappingException, IOException {

		System.out.println("investigation " + investigation.toString());

		Investigation resultInvestigation = investigationService
				.createInvestigationProviderInvestigationId(providerInvestigationId, categoryId, investigation);

		if (resultInvestigation != null) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.badRequest().body("Fail to create");
		}
	}

	// http://localhost:8080/investigation
	@GetMapping("/investigation")
	public ResponseEntity<List<Investigation>> fetchInvestigation() {

		List<Investigation> investigation_list = investigationService.fetchAllInvestigation();

		return ResponseEntity.ok().body(investigation_list);
	}

	// http://localhost:8080/investigation
	@GetMapping("/investigation/{selectedItemString}")
	public ResponseEntity<List<Investigation>> fetchInvestigationByInvestigationId(
			@PathVariable(value = "selectedItemString") String selectedItemString) {

		String selectedItem[] = selectedItemString.split(",");
		List<Investigation> investigation_list = investigationService.fetchInvestigationBySelectedItem(selectedItem);

		return ResponseEntity.ok().body(investigation_list);
	}

	// http://localhost:8080/investigationprovider/1
	@GetMapping("/investigationprovider/{providerId}")
	public ResponseEntity<List<Investigation>> fetchInvestigationByProviderId(
			@PathVariable(value = "providerId") int providerId) {

		List<Investigation> investigation_list = investigationService.fetchInvestigationByProviderId(providerId);

		return ResponseEntity.ok().body(investigation_list);
	}

	// http://localhost:8080/investigation/1/RBC
	@GetMapping("/investigation/{providerId}/{investigationName}")
	public ResponseEntity<FormatResponce> fetchInvestigationFormat(@PathVariable(value = "providerId") int providerId,
			@PathVariable(value = "investigationName") String investigationName) {

		FormatResponce formatResponce = investigationService
				.fetchInvestigationFormatByProviderIdAndInvestigationName(providerId, investigationName);

		return ResponseEntity.ok().body(formatResponce);
	}

	// http://localhost:8080/investigationproviderIdname/1
	@GetMapping("/investigationproviderIdname/{providerId}")
	public ResponseEntity<List<String>> test(@PathVariable(value = "providerId") int providerId) {

		List<String> investigation_list = investigationRepository.getNameAndRateOnly(providerId);

		return ResponseEntity.ok().body(investigation_list);
	}

	// http://localhost:8080/investigationname/1
	@GetMapping("/investigationname/{providerId}")
	ResponseEntity<List<String>> investigationNameList(@PathVariable(value = "providerId") int providerId)
			throws JsonParseException, JsonMappingException, IOException {

		List<String> resultInvestigationRequestNameList = investigationService.investigationNameList(providerId);

		return ResponseEntity.ok().body(resultInvestigationRequestNameList);

	}

	// http://localhost:8080/investigation/1/75/14
	@PostMapping("/investigation/{providerId}/{investigationId}/{categoryId}")
	ResponseEntity<?> updateInvestigation(@PathVariable(value = "investigationId") int investigationId,
			@PathVariable(value = "providerId") int providerId, @PathVariable(value = "categoryId") int categoryId,
			@RequestBody Investigation investigation) throws JsonParseException, JsonMappingException, IOException {

		Investigation resultInvestigation = investigationService.updateInvestigation(investigationId, providerId,
				categoryId, investigation);

		if (resultInvestigation != null) {
			return ResponseEntity.ok().body("successfully updated");
		} else {
			return ResponseEntity.badRequest().body("Fail to update");
		}
	}

	// http://localhost:8080/investigation/1
	@DeleteMapping("/investigation/{investigationId}")
	public ResponseEntity<?> deleteInvestigation(@PathVariable(value = "investigationId") int investigationId) {

		int delete = investigationService.deleteInvestigation(investigationId);
		if (delete == 0) {
			return ResponseEntity.badRequest().body("Investigation is not present in database");
		}

		return ResponseEntity.ok().body("Investigation is successfully deleted");

	}

}

//
//{
//	"name":"name",
//	"description":"description",
//	"note":"note",
//	"rate":200
//}
