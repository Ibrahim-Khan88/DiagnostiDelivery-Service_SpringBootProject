package com.diagnostic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagnostic.model.InvestigationRequest;
import com.diagnostic.model.LabDoctor;
import com.diagnostic.repository.InvestigationRequestRepository;
import com.diagnostic.repository.LabDoctorRepository;

@Service
public class LabDoctorService {

	@Autowired
	private LabDoctorRepository labDoctorRepository;

	@Autowired
	private InvestigationRequestRepository investigationRequestRepository;

	public boolean assignLabDoctor(String labDoctorName, int investigationRequestId) {

		Optional<InvestigationRequest> investigationRequest = investigationRequestRepository
				.findById(investigationRequestId);
		LabDoctor labDoctor = labDoctorRepository.findByTitle(labDoctorName);

		if (investigationRequest.isPresent() && labDoctor != null) {
			investigationRequest.get().setLabDoctor(labDoctor);
			investigationRequestRepository.save(investigationRequest.get());
			return true;
		}

		return false;
	}

}
