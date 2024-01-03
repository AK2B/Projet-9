package com.microservice.assessment.model;

import java.util.List;

public class AssessmentRequest {

    private String patientId;
    private List<String> notes;

    public AssessmentRequest() {
    }

    public AssessmentRequest(String patientId, List<String> notes) {
        this.patientId = patientId;
        this.notes = notes;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
