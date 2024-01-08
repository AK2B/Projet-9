package com.microservice.APIpatient.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.APIpatient.model.Address;
import com.microservice.APIpatient.model.Patient;
import com.microservice.APIpatient.repository.AddressRepository;
import com.microservice.APIpatient.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients from the database...");
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Transactional
    public Patient savePatientWithAddress(Patient patient) {
        Address address = patient.getAddress();
        if (address != null) {
            addressRepository.save(address);
        }

        // Save patient after updating the address
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> optionalExistingPatient = patientRepository.findById(id);

        return optionalExistingPatient.map(existingPatient -> {
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());
            existingPatient.setBirthday(updatedPatient.getBirthday());
            existingPatient.setGender(updatedPatient.getGender());

            Address updatedAddress = updatedPatient.getAddress();
            if (updatedAddress != null && !Objects.equals(existingPatient.getAddress(), updatedAddress)) {
                // Ensure the bidirectional relationship is maintained
                addressRepository.save(updatedAddress);
                existingPatient.setAddress(updatedAddress);
            }

            existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());

            // Save patient after updating information
            return patientRepository.save(existingPatient);
        }).orElse(null);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
