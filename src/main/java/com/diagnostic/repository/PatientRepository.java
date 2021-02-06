package com.diagnostic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diagnostic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	Patient findByBillNumber(String patientBillNumber);

	@Query("SELECT p.id FROM Patient p WHERE id=(SELECT max(id) FROM Patient)")
	int LastId();

}
