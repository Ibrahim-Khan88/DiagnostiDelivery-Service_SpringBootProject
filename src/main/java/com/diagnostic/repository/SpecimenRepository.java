package com.diagnostic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Specimen;

public interface SpecimenRepository extends JpaRepository<Specimen, Integer> {

}
