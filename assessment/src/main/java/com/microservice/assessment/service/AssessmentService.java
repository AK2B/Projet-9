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

@Service
public class AssessmentService {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class.getName());

    private static final List<String> TRIGGERS = List.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    /**
     * Assesses the patient's diabetes risk based on their patient details and notes.
     *
     * @param patient The patient's details
     * @param notes The patient's notes
     * @return The assessment level
     */
    public Assessment assess(PatientDto patient, List<NotesDto> notes) {
        int count = calculateTriggeringTermCount(notes);

        Assessment assessment = new Assessment();
        int age = patient.getAge();
        String gender = patient.getGender();

        logger.info("Number of triggering terms: " + count);
        logger.info("Patient age: " + age);
        logger.info("Patient gender: " + gender);

        if (count < 2) {
            logger.info("Assessment Level: NONE");
            assessment.setLevel(AssessmentLevel.NONE);
        } else if (age > 30) {
            if (count >= 2 && count <= 5) {
                logger.info("Assessment Level: BORDERLINE");
                assessment.setLevel(AssessmentLevel.BORDERLINE);
            } else if (gender.equals("M") && count >= 3) {
                logger.info("Assessment Level: IN_DANGER (Male)");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else if (gender.equals("F") && count >= 4) {
                logger.info("Assessment Level: IN_DANGER (Female)");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else if ((gender.equals("M") && count >= 5) || (gender.equals("F") && count >= 7) || count >= 8) {
                logger.info("Assessment Level: EARLY_ONSET");
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            } else {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            }
        } else {
            if ((gender.equals("M") && count >= 5) || (gender.equals("F") && count >= 7) || count >= 8) {
                logger.info("Assessment Level: EARLY_ONSET");
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            } else if (count >= 6 && count <= 7) {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            } else {
                logger.info("Assessment Level: IN_DANGER");
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            }
        }

        return assessment;
    }


    private int calculateTriggeringTermCount(List<NotesDto> notes) {
        int count = 0;

        for (NotesDto note : notes) {
            if (note.getNotes() != null) {
                // Utiliser un ensemble pour stocker les déclencheurs déjà rencontrés pour cette note
                Set<String> encounteredTriggers = new HashSet<>();

                for (String noteElement : note.getNotes()) {
                    noteElement = noteElement.toLowerCase();

                    // Logger
                    logger.info("Processing note element: " + noteElement);

                    for (String trigger : TRIGGERS) {
                        int index = noteElement.indexOf(trigger.toLowerCase());
                        while (index != -1) {
                            // Vérifier si le déclencheur a déjà été rencontré pour cette note
                            if (!encounteredTriggers.contains(trigger)) {
                                count++;
                                encounteredTriggers.add(trigger);  // Ajouter le déclencheur à l'ensemble
                                logger.info("Trigger term found: " + trigger + " in notes: " + note.getNotes());
                            }
                            index = noteElement.indexOf(trigger.toLowerCase(), index + trigger.length());
                        }
                    }
                }
            }
        }

        // Logger
        logger.info("Total triggering terms count: " + count);
        return count;
    }

}
