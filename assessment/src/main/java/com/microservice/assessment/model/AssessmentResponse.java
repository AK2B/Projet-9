package com.microservice.assessment.model;

public class AssessmentResponse {

    private AssessmentLevel level;

    public AssessmentResponse() {
    }

    public AssessmentResponse(Assessment assessment) {
        this.level = assessment.getLevel();
    }

    public AssessmentLevel getLevel() {
        return level;
    }

    public void setLevel(AssessmentLevel level) {
        this.level = level;
    }
}
