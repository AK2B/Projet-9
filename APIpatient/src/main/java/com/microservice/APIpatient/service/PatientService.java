package com.microservice.APIpatient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.APIpatient.model.Patient;
import com.microservice.APIpatient.repository.PatientRepository;

@Service
public class PatientService {

	private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> getAllPatients() {
		logger.info("Fetching all patients from the database...");
		return patientRepository.findAll();
	}

	public Patient getPatientById(Long id) {
		return patientRepository.findById(id).orElse(null);
	}
	
	public Patient savePatient(Patient patient) {
		return patientRepository.save(patient);
	}

	public Patient updatePatient(Long id, Patient updatedPatient) {
		Patient existingPatient = patientRepository.findById(id).orElse(null);
		if (existingPatient != null) {
			existingPatient.setFirstName(updatedPatient.getFirstName());
			existingPatient.setLastName(updatedPatient.getLastName());
			patientRepository.save(existingPatient);
		}
		return existingPatient;
	}

	public void deletePatient(Long id) {
		patientRepository.deleteById(id);
	}

}
