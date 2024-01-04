package com.microservice.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.assessment.model.Assessment;
import com.microservice.assessment.model.AssessmentResponse;
import com.microservice.assessment.model.NotesDto;
import com.microservice.assessment.model.PatientDto;
import com.microservice.assessment.service.AssessmentService;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, WebClient.Builder webClientBuilder) {
        this.assessmentService = assessmentService;
        this.webClientBuilder = webClientBuilder;
    }

    @GetMapping
    public ResponseEntity<String> assessment() {
    	return ResponseEntity.ok("bonjour");
    }
    
    @GetMapping("/{patientId}")
    public AssessmentResponse assess(@RequestParam String patientId) {
        // Retrieve patient data
        PatientDto patient = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/patients/{id}", patientId)
                .retrieve()
                .bodyToMono(PatientDto.class)
                .block();  // Note: blocking operation, consider using reactive programming

        // Retrieve patient notes
        NotesDto notes = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/notes/{id}", patientId)
                .retrieve()
                .bodyToMono(NotesDto.class)
                .block();  // Note: blocking operation, consider using reactive programming

        // Calculate the assessment
        Assessment assessment = assessmentService.assess(patient, notes);

        // Create the response
        AssessmentResponse response = new AssessmentResponse();
        response.setLevel(assessment.getLevel());

        return response;
    }
}
