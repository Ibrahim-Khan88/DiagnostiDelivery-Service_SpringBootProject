package com.diagnostic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	Department findByName(String patientBillNumber);

}
