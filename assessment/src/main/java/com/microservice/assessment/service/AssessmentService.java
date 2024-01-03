package com.microservice.assessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentLevel;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;

@Service
public class AssessmentService {

    private static final List<String> TRIGGERS = List.of(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse",
            "Anormal", "Cholestérol", "Vertiges", "Rechute", "Réaction", "Anticorps"
    );

    public Assessment assess(PatientDto patient, NotesDto notes) {
        // Calculate the number of triggering terms
        int count = 0;
        for (String note : notes.getNotes()) {
            for (String trigger : TRIGGERS) {
                if (note.toLowerCase().contains(trigger.toLowerCase())) {
                    count++;
                    break;
                }
            }
        }

        // Update assessment based on age and gender
        Assessment assessment = new Assessment();
        if (count == 0) {
            assessment.setLevel(AssessmentLevel.NONE);
        } else if (count >= 2 && patient.getAge() >= 30) {
            if ("M".equals(patient.getGender())) {
                if (count >= 3) {
                    assessment.setLevel(AssessmentLevel.IN_DANGER);
                }
            } else if ("F".equals(patient.getGender())) {
                if (count >= 4) {
                    assessment.setLevel(AssessmentLevel.IN_DANGER);
                }
            }
        } else {
            if ((count >= 6 && count <= 7) || count >= 8) {
                assessment.setLevel(AssessmentLevel.EARLY_ONSET);
            } else {
                assessment.setLevel(AssessmentLevel.IN_DANGER);
            }
        }

        return assessment;
    }
}
