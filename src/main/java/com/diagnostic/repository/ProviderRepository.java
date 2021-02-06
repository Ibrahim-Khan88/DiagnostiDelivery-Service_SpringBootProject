package com.diagnostic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagnostic.model.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

}
