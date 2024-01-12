package com.microservice.UI.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.UI.model.PatientDetailsDto;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/patient")
public class ListController {

	private static final Logger logger = LoggerFactory.getLogger(ListController.class);
	private final WebClient webClient = WebClient.create();

	@Value("${api.base-url}")
	private String apiBaseUrl;

	@GetMapping("/list")
	public String listPatient(Model model) {

		List<PatientDetailsDto> patients = webClient.get().uri(apiBaseUrl + "/patients")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password")).retrieve()
				.bodyToFlux(PatientDetailsDto.class).collectList().block();

		model.addAttribute("patients", patients);

		return "patient/list";
	}

	@GetMapping("/add")
	public String addPatient(Model model) {
		model.addAttribute("patient", new PatientDetailsDto());
		return "patient/add";
	}

	@PostMapping("/add")
	public String savePatient(@ModelAttribute("patient") PatientDetailsDto patient, Model model) {
		logger.info("Patient details received: {}", patient.toString());

		webClient.post().uri(apiBaseUrl + "/patients")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.body(Mono.just(patient), PatientDetailsDto.class).retrieve().bodyToMono(PatientDetailsDto.class)
				.doOnSuccess(updatedPatient -> logger.info("Patient added successfully: {}", updatedPatient)).block();

		return "redirect:/patient/list";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable Long id, Model model) {
		PatientDetailsDto patientToUpdate = webClient.get().uri(apiBaseUrl + "/patients/{id}", id)
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password")).retrieve()
				.bodyToMono(PatientDetailsDto.class).block();

		model.addAttribute("patient", patientToUpdate);

		return "patient/update";
	}

	@PostMapping("/update")
	public String updatePatient(@ModelAttribute("patient") PatientDetailsDto patient, Model model) {
		logger.info("Updating patient with ID: {}", patient.getId());

		webClient.put().uri(apiBaseUrl + "/patients/{id}", patient.getId())
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.body(Mono.just(patient), PatientDetailsDto.class).retrieve().bodyToMono(PatientDetailsDto.class)
				.doOnSuccess(updatedPatient -> logger.info("Patient updated successfully: {}", updatedPatient)).block();

		return "redirect:/patient/list";
	}

}