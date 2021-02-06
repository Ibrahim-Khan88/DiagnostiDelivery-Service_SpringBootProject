package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Integer> {

	List<Parameter> findByInvestigationIdAndParamGroupId(int investigationID, int paramGroupId);

	Parameter findByIdAndInvestigationId(int parameterId, int investigationID);
	// List<Parameter> findByInvestigationId(int investigationID);

}
