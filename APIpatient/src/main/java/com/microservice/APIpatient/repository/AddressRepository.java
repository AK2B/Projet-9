package com.microservice.APIpatient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.APIpatient.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
