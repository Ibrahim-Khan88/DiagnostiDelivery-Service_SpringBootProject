package com.diagnostic.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.service.InvestivationParameterResultService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
public class InvestivationParameterResultController {

	@Autowired
	private InvestivationParameterResultService investivationParameterResultService;

	// http://localhost:8080/setresultparameter/537
	@PostMapping("/setresultparameter/{investigationRequestId}")
	ResponseEntity<?> createInvestigationRequest(
			@PathVariable(value = "investigationRequestId") int investigationRequestId,
			@RequestParam("parameterIdList") String parameterIdList, @RequestParam("resultList") String resultList,
			@RequestParam("labDoctorName") String labDoctorTitle)
			throws JsonParseException, JsonMappingException, IOException {

		boolean result = investivationParameterResultService.saveResultParameter(investigationRequestId,
				parameterIdList, resultList, labDoctorTitle);

		if (result) {
			return ResponseEntity.ok().body("successfully updated");
		} else {
			return ResponseEntity.badRequest().body("Fail to update");
		}

		// 1,2,3,4,5,6
		// positive,negative,positive,negative,positive,negative

	}

}
