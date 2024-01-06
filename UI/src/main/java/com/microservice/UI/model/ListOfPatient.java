package com.microservice.UI.model;

import java.util.List;

public class ListOfPatient {

    private List<PatientDetailsDto> patients;

    public ListOfPatient(List<PatientDetailsDto> patients) {
        this.patients = patients;
    }

    public List<PatientDetailsDto> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDetailsDto> patients) {
        this.patients = patients;
    }
}
