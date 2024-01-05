package com.microservice.assessment.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentLevel;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;

/**
 * Service class for assessing the diabetes risk of a patient based on patient details and notes.
 */
@Service
public class AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class.getName());

    private static final List<String> TRIGGERS = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids",
            "Fumeur", "Fumeuse", "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps");

    /**
     * Assesses the patient's diabetes risk based on their patient details and notes.
     *
     * @param patient The patient's details.
     * @param notes   The patient's notes.
     * @return The assessment level.
     */
    public Assessment assess(PatientDto patient, List<NotesDto> notes) {
        int count = calculateTriggeringTermCount(notes);

        Assessment assessment = new Assessment();
        int age = patient.getAge();
        String gender = patient.getGender();

        logger.info("Patient age: " + age);
        logger.info("Patient gender: " + gender);

        if (age > 30) {
            if (count < 2) {
                logger.info("Assessment Level: NONE");
                assessment.setLevel(AssessmentLevel.NONE);
            } else if (count >= 2 && count <= 5) {
                logger.info("Assessment Level: BORDERLINE");
                assessment.setLevel(AssessmentLevel.BORDERLINE);
            } else if (count >= 6 && count <= 7) {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else if (count >= 8) {
                logger.info("Assessment Level: EARLY_ONSET");
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            }
        } else if (age < 30 && gender.equals("M")) {
            if (count < 3) {
                logger.info("Assessment Level: NONE");
                assessment.setLevel(AssessmentLevel.NONE);
            } else if (count >= 3 && count <= 4) {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else if (count >= 5) {
                logger.info("Assessment Level: EARLY_ONSET");
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            }
        } else if (age < 30 && gender.equals("F")) {
            if (count < 4) {
                logger.info("Assessment Level: NONE");
                assessment.setLevel(AssessmentLevel.NONE);
            } else if (count >= 4 && count <= 6) {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else if (count >= 7) {
                logger.info("Assessment Level: EARLY_ONSET");
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            }
        }

        return assessment;
    }

    /**
     * Calculates the count of triggering terms in the patient's notes.
     *
     * @param notes The patient's notes.
     * @return The count of triggering terms.
     */
    public int calculateTriggeringTermCount(List<NotesDto> notes) {
        int count = 0;

        for (NotesDto note : notes) {
            if (note.getNotes() != null) {
                Set<String> encounteredTriggers = new HashSet<>();

                for (String noteElement : note.getNotes()) {
                    noteElement = noteElement.toLowerCase();

                    for (String trigger : TRIGGERS) {
                        int index = noteElement.indexOf(trigger.toLowerCase());
                        while (index != -1) {
                            if (!encounteredTriggers.contains(trigger)) {
                                count++;
                                encounteredTriggers.add(trigger);
                                logger.info("Trigger term found: " + trigger + " in notes: " + note.getNotes());
                            }
                            index = noteElement.indexOf(trigger.toLowerCase(), index + trigger.length());
                        }
                    }
                }
            }
        }
        logger.info("Total triggering terms count: " + count);
        return count;
    }
}
