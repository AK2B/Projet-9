package com.microservice.APIpatient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.microservice.APIpatient.model.Patient;
import com.microservice.APIpatient.repository.PatientRepository;
import com.microservice.APIpatient.service.PatientService;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatients() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());

        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPatientById() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(patientId);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testSavePatient() {
        // Arrange
        Patient patient = new Patient();
        when(patientRepository.save(patient)).thenReturn(patient);

        // Act
        Patient result = patientService.savePatient(patient);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        Long patientId = 1L;
        Patient existingPatient = new Patient();
        existingPatient.setId(patientId);
        existingPatient.setFirstName("John");

        Patient updatedPatient = new Patient();
        updatedPatient.setId(patientId);
        updatedPatient.setFirstName("Updated");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(existingPatient)).thenReturn(existingPatient);

        // Act
        Patient result = patientService.updatePatient(patientId, updatedPatient);

        // Assert
        assertEquals("Updated", result.getFirstName());
    }

    @Test
    public void testDeletePatient() {
        // Arrange
        Long patientId = 1L;

        // Act
        patientService.deletePatient(patientId);

        // Assert
        verify(patientRepository, times(1)).deleteById(patientId);
    }
}
