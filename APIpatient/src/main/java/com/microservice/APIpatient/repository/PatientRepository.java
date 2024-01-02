package com.microservice.APIpatient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.APIpatient.model.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	

}