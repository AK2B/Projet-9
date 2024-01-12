package com.microservice.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Controller for handling assessment-related HTTP requests.
 */
@RestController
@RequestMapping("/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;
    
    private final WebClient webClient = WebClient.create();
    
    @Value("${api.base-url}")
	private String apiBaseUrl;
    
    /**
     * Endpoint for assessing the diabetes risk of a patient.
     *
     * @param patientId The unique identifier of the patient.
     * @return The assessment response.
     */
    @GetMapping("/{patientId}")
    public AssessmentResponse assess(@PathVariable String patientId) {
        PatientDto patient = webClient.get()       		
                .uri(apiBaseUrl + "/patients/{id}", patientId)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
                .retrieve()
                .bodyToMono(PatientDto.class)
                .block();

        List<NotesDto> notes = webClient.get()         
        		.uri(apiBaseUrl + "/notes/{id}", patientId)
        		.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
                .retrieve()
                .bodyToFlux(NotesDto.class)
                .collectList()
                .block();

        Assessment assessment = assessmentService.assess(patient, notes);

        AssessmentResponse response = new AssessmentResponse(assessment);

        return response;
    }
}