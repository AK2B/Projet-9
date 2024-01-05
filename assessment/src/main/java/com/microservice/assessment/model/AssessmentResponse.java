package com.microservice.assessment.model;

/**
 * Represents the response for an assessment, including the assessment level.
 */
public class AssessmentResponse {

	private AssessmentLevel level;

	public AssessmentResponse() {
	}

	/**
	 * Constructor for AssessmentResponse.
	 *
	 * @param assessment The assessment from which to extract the level.
	 */
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
