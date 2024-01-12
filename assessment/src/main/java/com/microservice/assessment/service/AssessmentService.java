package com.microservice.assessment.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentLevel;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;

/**
 * Service class for assessing the diabetes risk of a patient based on patient
 * details and notes.
 */
@Service
public class AssessmentService {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class.getName());

	private static final List<String> TRIGGERS = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
			"Fumeur", "Fumeuse", "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps");

	/**
	 * Assesses the patient's diabetes risk based on their patient details and
	 * notes.
	 *
	 * @param patient The patient's details.
	 * @param notes   The patient's notes.
	 * @return The assessment level.
	 */
	public Assessment assess(PatientDto patient, List<NotesDto> notes) {
		
		int count = calculateTriggeringTermCount(notes);
		int age = patient.getAge();
		String gender = patient.getGender();
		
		Assessment assessment = new Assessment();
		
		if (age >= 30) {
			
			assessAgeOver30(count, assessment);
			
		} else if (age < 30) {
			
			switch (gender) {
			case "M":
				assessMaleUnder30(count, assessment);
				break;
			case "F":
				assessFemaleUnder30(count, assessment);
				break;
			}
		}

		return assessment;
	}

	private void assessAgeOver30(int count, Assessment assessment) {
		if (count < 2) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.NONE);
		} else if (count >= 2 && count <= 5) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.BORDERLINE);
		} else if (count >= 6 && count <= 7) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.IN_DANGER);
		} else if (count >= 8) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.EARLY_ONSET);
		}
	}

	private void assessMaleUnder30(int count, Assessment assessment) {
		if (count < 3) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.NONE);
		} else if (count >= 3 && count <= 4) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.IN_DANGER);
		} else if (count >= 5) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.EARLY_ONSET);
		}
	}

	private void assessFemaleUnder30(int count, Assessment assessment) {
		if (count < 4) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.NONE);
		} else if (count >= 4 && count <= 6) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.IN_DANGER);
		} else if (count >= 7) {
			setAssessmentLevelAndLog(assessment, AssessmentLevel.EARLY_ONSET);
		}
	}

	private void setAssessmentLevelAndLog(Assessment assessment, AssessmentLevel level) {
		logger.info("Assessment Level: " + level);
		assessment.setLevel(level);
	}

	/**
	 * Calculates the count of triggering terms in the patient's notes.
	 *
	 * @param notes The patient's notes.
	 * @return The count of triggering terms.
	 */
	public int calculateTriggeringTermCount(List<NotesDto> notes) {
		Set<String> encounteredTriggers = new HashSet<>();

		return notes.stream().map(NotesDto::getNotes).filter(Objects::nonNull).flatMap(Collection::stream)
				.map(String::toLowerCase).flatMap(noteElement -> TRIGGERS.stream().filter(trigger -> {
					int index = noteElement.indexOf(trigger.toLowerCase());
					while (index != -1) {
						if (!encounteredTriggers.contains(trigger)) {
							encounteredTriggers.add(trigger);
							logger.info("Trigger term found: " + noteElement);
							return true;
						}
						index = noteElement.indexOf(trigger.toLowerCase(), index + trigger.length());
					}
					return false;
				})).mapToInt(value -> 1).sum();
	}

}
