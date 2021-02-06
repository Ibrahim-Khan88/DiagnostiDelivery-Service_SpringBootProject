package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.LabDoctor;

public interface LabDoctorRepository extends JpaRepository<LabDoctor, Integer> {

	List<LabDoctor> findByProviderId(int providerId);

	LabDoctor findByTitle(String title);

}
