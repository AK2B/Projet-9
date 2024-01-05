package com.microservice.assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentLevel;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;
import com.microservice.assessment.service.AssessmentService;

@SpringBootTest
class AssessmentServiceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private AssessmentService assessmentService;

    @Test
    void assess_NoneRisk() {
        PatientDto patient = new PatientDto(1L, "Doe", "John", "1990-01-01", "M", null);
        List<NotesDto> notes = new ArrayList<>();

        Assessment result = assessmentService.assess(patient, notes);

        assertEquals(AssessmentLevel.NONE, result.getLevel());
    }

    @Test
    void assess_BorderlineRisk() {
        PatientDto patient = new PatientDto(1L, "Doe", "Jane", "1991-01-01", "F", null);
        List<NotesDto> notes = createNotesList("Hémoglobine A1C", "Microalbumine");

        Assessment result = assessmentService.assess(patient, notes);

        assertEquals(AssessmentLevel.BORDERLINE, result.getLevel());
    }

    @Test
    void assess_InDangerRisk_Male() {
        PatientDto patient = new PatientDto(1L, "Doe", "Bob", "1998-01-01", "M", null);
        List<NotesDto> notes = createNotesList("Hémoglobine A1C", "Microalbumine", "Fumeur");

        Assessment result = assessmentService.assess(patient, notes);

        assertEquals(AssessmentLevel.IN_DANGER, result.getLevel());
    }

    @Test
    void assess_InDangerRisk_Female() {
        PatientDto patient = new PatientDto(1L, "Doe", "Alice", "1999-01-01", "F", null);
        List<NotesDto> notes = createNotesList("Hémoglobine A1C", "Microalbumine", "Fumeuse", "Cholestérol");

        Assessment result = assessmentService.assess(patient, notes);

        assertEquals(AssessmentLevel.IN_DANGER, result.getLevel());
    }

    @Test
    void assess_EarlyOnsetRisk() {
        PatientDto patient = new PatientDto(1L, "Doe", "Eve", "1970-01-01", "F", null);
        List<NotesDto> notes = createNotesList("Hémoglobine A1C", "Microalbumine", "Fumeuse", "Cholestérol", "Réaction", "Taille", "Poids", "Fumeur");

        Assessment result = assessmentService.assess(patient, notes);

        assertEquals(AssessmentLevel.EARLY_ONSET, result.getLevel());
    }

    @Test
    void calculateTriggeringTermCount() {
        List<NotesDto> notes = createNotesList("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse");
        int result = assessmentService.calculateTriggeringTermCount(notes);

        assertEquals(6, result);
    }

    private List<NotesDto> createNotesList(String... noteContents) {
        List<NotesDto> notes = new ArrayList<>();
        for (String content : noteContents) {
            NotesDto note = new NotesDto();
            note.setNotes(List.of(content));
            notes.add(note);
        }
        return notes;
    }
}
