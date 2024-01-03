package com.microservice.assessment.model;

public class AssessmentResponse {

    private AssessmentLevel level;

    public AssessmentResponse() {
    }

    public AssessmentResponse(AssessmentLevel level) {
        this.level = level;
    }

    public AssessmentLevel getLevel() {
        return level;
    }

    public void setLevel(AssessmentLevel level) {
        this.level = level;
    }
}
