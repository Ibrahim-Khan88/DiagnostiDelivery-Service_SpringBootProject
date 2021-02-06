package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	List<Doctor> findByProviderId(int providerId);

}
