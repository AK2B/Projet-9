package com.microservice.assessment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentResponse;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;
import com.microservice.assessment.service.AssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/assessment")
@RequiredArgsConstructor
public class AssessmentController {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

	@Autowired
    private AssessmentService assessmentService;
    private final WebClient webClient = WebClient.create();

    @GetMapping("/greeting")
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok(("Hello from the AssessmentController!"));
    }

    @GetMapping("/{patientId}")
    public AssessmentResponse assess(@PathVariable String patientId) {
        PatientDto patient = webClient.get()
                .uri("http://localhost:8081/patients/{id}", patientId)
                .retrieve()
                .bodyToMono(PatientDto.class)
                .block();

        List<NotesDto> notes = webClient.get()
                .uri("http://localhost:8081/notes/{id}", patientId)
                .retrieve()
                .bodyToFlux(NotesDto.class)
                .collectList()
                .block();
        
        Assessment assessment = assessmentService.assess(patient, notes);

        AssessmentResponse response = new AssessmentResponse(assessment);
        return response;
    }
}
