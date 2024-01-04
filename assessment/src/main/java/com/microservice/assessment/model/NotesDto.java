package com.microservice.assessment.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microservice.assessment.service.CustomStringListDeserializer;

public class NotesDto {

    @JsonProperty("id")
    private String patientId;

    @JsonProperty("note")
    @JsonDeserialize(using = CustomStringListDeserializer.class)
    private List<String> notes;

    public NotesDto() {
    }

    public NotesDto(String id, String patientId, List<String> notes) {
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
