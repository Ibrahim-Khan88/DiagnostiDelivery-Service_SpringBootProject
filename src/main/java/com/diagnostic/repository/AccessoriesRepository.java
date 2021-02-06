package com.diagnostic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Accessories;

public interface AccessoriesRepository extends JpaRepository<Accessories, Integer> {

}
