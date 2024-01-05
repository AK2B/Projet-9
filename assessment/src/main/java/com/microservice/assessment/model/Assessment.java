package com.microservice.assessment.model;

/**
 * Represents the result of an assessment, including the assessment level.
 */
public class Assessment {

	private AssessmentLevel level;

	public Assessment() {
	}

	/**
	 * Get the assessment level.
	 *
	 * @return The assessment level.
	 */
	public AssessmentLevel getLevel() {
		return level;
	}

	public void setLevel(AssessmentLevel level) {
		this.level = level;
	}
}
