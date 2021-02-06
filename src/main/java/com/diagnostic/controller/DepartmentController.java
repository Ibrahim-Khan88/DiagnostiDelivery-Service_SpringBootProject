package com.diagnostic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.diagnostic.model.Department;
import com.diagnostic.model.ParamGroup;
import com.diagnostic.model.Parameter;
import com.diagnostic.repository.DepartmentRepository;
import com.diagnostic.repository.ParamGroupRepository;
import com.diagnostic.repository.ParameterRepository;

@CrossOrigin("*")
@RestController
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ParameterRepository parameterRepository;

	@Autowired
	private ParamGroupRepository paramGroupRepository;

	// http://localhost:8080/department
	@GetMapping("/department")
	public ResponseEntity<List<Department>> fetchDepartment() {

		List<Department> department_list = departmentRepository.findAll();

		return ResponseEntity.ok().body(department_list);
	}

	// http://localhost:8080/parameter
	@GetMapping("/parameter")
	public ResponseEntity<List<Parameter>> parameter() {

		// List<Parameter> department_list =
		// parameterRepository.findByInvestigationId(378);

		List<Parameter> department_list = parameterRepository.findAll(Sort.by(Sort.Direction.DESC, "shortingId"));

		return ResponseEntity.ok().body(department_list);
	}

	// http://localhost:8080/paramgroup
	@GetMapping("/paramgroup/{departmentId}/{subGroupId}")
	public ResponseEntity<List<ParamGroup>> paramgroup(@PathVariable(value = "departmentId") int departmentId,
			@PathVariable(value = "subGroupId") int subGroupId) {

		List<ParamGroup> department_list = paramGroupRepository.findByDepartmentsIdAndSubGroupsId(departmentId,
				subGroupId);

		return ResponseEntity.ok().body(department_list);
	}

}
