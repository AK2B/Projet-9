package com.microservice.UI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.UI.model.PatientDetailsDto;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/patient")
public class ListController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);

	private final WebClient webClient = WebClient.create();

	@GetMapping("/list")
	public String listPatient(Model model) {
		
		List<PatientDetailsDto> patients =  webClient.get()				
				.uri("http://localhost:8081/patients")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.retrieve()
				.bodyToFlux(PatientDetailsDto.class)
				.collectList()
				.block();
        
        model.addAttribute("patients", patients);

		return "patient/list";
	}

	@GetMapping("/add")
	public String addPatient() {

		return "patient/add";
	}

	@PostMapping("/add")
	public String savepPatient(@ModelAttribute("patient") PatientDetailsDto patient, Model model) {

		webClient.post()
				.uri("http://localhost:8081/patients")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.body(Mono.just(patient), PatientDetailsDto.class)
				.retrieve()
				.bodyToMono(PatientDetailsDto.class)
				.block();

		return "redirect:/patient/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable Long id, @RequestParam String lastName, @RequestParam String firstName,
			@RequestParam String birthday, @RequestParam String gender, @RequestParam String address,
			@RequestParam String phoneNumber, Model model) {

		PatientDetailsDto patientToUpdate = new PatientDetailsDto();
		patientToUpdate.setId(id);
		patientToUpdate.setLastName(lastName);
		patientToUpdate.setFirstName(firstName);
		patientToUpdate.setBirthday(birthday);
		patientToUpdate.setGender(gender);
		patientToUpdate.setAddress(address);
		patientToUpdate.setPhoneNumber(phoneNumber);

		model.addAttribute("patient", patientToUpdate);

		return "patient/update";
	}

	 @PostMapping("/update")
	    public String updatePatient(@ModelAttribute("patient") PatientDetailsDto patient, Model model) {
	        logger.info("Updating patient with ID: {}", patient.getId());

	        webClient
	        		.put().uri("http://localhost:8081/patients/{id}", patient.getId())
					.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
	                .body(Mono.just(patient), PatientDetailsDto.class)
	                .retrieve()
	                .bodyToMono(PatientDetailsDto.class)
	                .doOnSuccess(updatedPatient -> logger.info("Patient updated successfully: {}", updatedPatient))
	                .block();

		return "redirect:/patient/list";
	}

}