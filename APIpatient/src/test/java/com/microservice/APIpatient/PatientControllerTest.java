package com.microservice.APIpatient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservice.APIpatient.controller.PatientController;
import com.microservice.APIpatient.model.Patient;
import com.microservice.APIpatient.service.PatientService;

public class PatientControllerTest {


    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

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

        when(patientService.getAllPatients()).thenReturn(patients);

        // Act
        List<Patient> result = patientController.getAllPatients();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPatientById() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        when(patientService.getPatientById(patientId)).thenReturn(patient);

        // Act
        ResponseEntity<Patient> result = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void testGetPatientByIdNotFound() {
        // Arrange
        Long patientId = 1L;
        when(patientService.getPatientById(patientId)).thenReturn(null);

        // Act
        ResponseEntity<Patient> result = patientController.getPatientById(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSavePatient() {
        // Arrange
        Patient patient = new Patient();
        when(patientService.savePatientWithAddress(patient)).thenReturn(patient);

        // Act
        ResponseEntity<Patient> result = patientController.savePatient(patient);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void testUpdatePatient() {
        // Arrange
        Long patientId = 1L;
        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("John");
        updatedPatient.setLastName("Doe");

        when(patientService.updatePatient(patientId, updatedPatient)).thenReturn(updatedPatient);

        // Act
        ResponseEntity<Patient> result = patientController.updatePatient(patientId, updatedPatient);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedPatient, result.getBody());

        verify(patientService, times(1)).updatePatient(patientId, updatedPatient);
    }
    
    @Test
    public void testUpdatePatientNotFound() {
        // Arrange
        Long patientId = 1L;
        Patient updatedPatient = new Patient();
        when(patientService.updatePatient(patientId, updatedPatient)).thenReturn(null);

        // Act
        ResponseEntity<Patient> result = patientController.updatePatient(patientId, updatedPatient);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeletePatient() {
        // Arrange
        Long patientId = 1L;

        // Act
        ResponseEntity<String> result = patientController.deletePatient(patientId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Patient deleted successfully", result.getBody());

        verify(patientService, times(1)).deletePatient(patientId);
    }
}
