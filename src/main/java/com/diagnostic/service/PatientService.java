package com.diagnostic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagnostic.model.Patient;
import com.diagnostic.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public boolean updatePaymentAmount(int amount, String billNumber) {

		Patient patient = patientRepository.findByBillNumber(billNumber);

		if (patient != null) {
			patient.setPaymentAmount(String.valueOf(amount));
			patientRepository.save(patient);
			return true;
		}

		return false;
	}

}
