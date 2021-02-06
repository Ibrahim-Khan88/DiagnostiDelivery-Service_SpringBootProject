package com.diagnostic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagnostic.model.InvestigationRequest;
import com.diagnostic.model.InvestivationParameterResult;
import com.diagnostic.model.LabDoctor;
import com.diagnostic.model.Parameter;
import com.diagnostic.repository.InvestigationRequestRepository;
import com.diagnostic.repository.InvestivationParameterResultRepository;
import com.diagnostic.repository.LabDoctorRepository;
import com.diagnostic.repository.ParameterRepository;

@Service
public class InvestivationParameterResultService {

	@Autowired
	private InvestigationRequestRepository investigationRequestRepository;

	@Autowired
	private LabDoctorRepository labDoctorRepository;

	@Autowired
	private ParameterRepository parameterRepository;

	@Autowired
	private InvestivationParameterResultRepository investivationParameterResultRepository;

	public boolean saveResultParameter(int investigationRequestId, String parameterIdListString,
			String resultListString, String labDoctorTitle) {

		String[] parameterIdList = parameterIdListString.split(",");
		String[] resultList = resultListString.split(",");

		for (int i = 0; i < parameterIdList.length; i++) {
			System.out.println("parameterIdList=" + parameterIdList[i]);
			System.out.println("resultList=" + resultList[i]);
		}

		Optional<InvestigationRequest> investigationRequest = investigationRequestRepository
				.findById(investigationRequestId);

		LabDoctor labDoctor = labDoctorRepository.findByTitle(labDoctorTitle);

		if (investigationRequest.isPresent()) {

			investigationRequest.get().setReportText("set");
			investigationRequest.get().setLabDoctor(labDoctor);
			investigationRequestRepository.save(investigationRequest.get());

			for (int i = 0; i < parameterIdList.length; i++) {

				InvestivationParameterResult investivationParameterResult = new InvestivationParameterResult();
				Optional<Parameter> parameter = parameterRepository.findById(Integer.parseInt(parameterIdList[i]));
				InvestivationParameterResult isExistsInvestivationParameter = investivationParameterResultRepository
						.findByInvestigationRequestIdAndParameterId(investigationRequestId,
								Integer.parseInt(parameterIdList[i]));

				if (isExistsInvestivationParameter != null) {
					investivationParameterResult.setId(isExistsInvestivationParameter.getId());
				}

				if (parameter.isPresent()) {
					investivationParameterResult.setInvestigationRequest(investigationRequest.get());
					investivationParameterResult.setParameter(parameter.get());

					if (resultList[i].equals("null")) {
						investivationParameterResult.setResult(null);
					} else {
						investivationParameterResult.setResult(resultList[i]);
					}

					investivationParameterResultRepository.save(investivationParameterResult);
				} else {
					return false;
				}

			}
		} else {
			return false;
		}

		return true;
	}

}
