package com.microservice.assessment.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Data Transfer Object representing patient details.
 */
public class PatientDto {

	private Long id;
	private String last_name;
	private String first_name;
	private String birthday;
	private String gender;
	private AssessmentLevel level;

	public PatientDto() {
	}

	/**
	 * Parameterized constructor for PatientDto.
	 *
	 * @param id         Unique identifier for the patient.
	 * @param last_name  Last name of the patient.
	 * @param first_name First name of the patient.
	 * @param birthday   Birthday of the patient.
	 * @param gender     Gender of the patient.
	 * @param level      Assessment level of the patient.
	 */
	public PatientDto(Long id, String last_name, String first_name, String birthday, String gender,
			AssessmentLevel level) {
		this.id = id;
		this.last_name = last_name;
		this.first_name = first_name;
		this.birthday = birthday;
		this.gender = gender;
		this.level = level;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public AssessmentLevel getLevel() {
		return level;
	}

	public void setLevel(AssessmentLevel level) {
		this.level = level;
	}

	public int getAge() {
		if (birthday != null) {
			LocalDate birthDate = LocalDate.parse(birthday);
			LocalDate currentDate = LocalDate.now();
			return Period.between(birthDate, currentDate).getYears();
		}
		return 0;
	}

}
