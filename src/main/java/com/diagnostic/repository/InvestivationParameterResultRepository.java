package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.InvestivationParameterResult;

public interface InvestivationParameterResultRepository extends JpaRepository<InvestivationParameterResult, Integer> {

	InvestivationParameterResult findByInvestigationRequestIdAndParameterId(int investigationRequestID,
			int parameterId);

	List<InvestivationParameterResult> findByInvestigationRequestId(int investigationRequestID);

}
