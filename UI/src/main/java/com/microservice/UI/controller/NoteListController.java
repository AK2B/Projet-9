package com.microservice.UI.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.UI.model.NotesDetailsDto;
import com.microservice.UI.model.Risk;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/history")
public class NoteListController {

	private final WebClient webClient = WebClient.create();
	
	@Value("${api.base-url}")
    private String apiBaseUrl;
	
	@GetMapping("/{patientId}")
	public String listPatient(@PathVariable String patientId, Model model) {
		List<NotesDetailsDto> notes = webClient.get()
				.uri(apiBaseUrl + "/notes/{id}", patientId)
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.retrieve()
				.bodyToFlux(NotesDetailsDto.class)
				.collectList()
				.block();

		model.addAttribute("notes", notes);
		
		Risk risk = webClient.get()				
				.uri(apiBaseUrl + "/assessment/{id}", patientId)
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
                .retrieve()
                .bodyToMono(Risk.class)
                .block();

		model.addAttribute("risk", risk);
		
		return "note/list";
	}
	
	@GetMapping("/add")
	public String addNote(Model model) {
		model.addAttribute("noteDto", new NotesDetailsDto());
	    return "note/add";
	}

	
	@PostMapping("/add")
	public String saveNote(@ModelAttribute("noteDto") NotesDetailsDto noteDto, Model model) {

	    ResponseEntity<Void> response = webClient.get()
	            .uri(apiBaseUrl + "/patients/{id}", noteDto.getPatId())
	            .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
	            .retrieve()
	            .toBodilessEntity()
	            .block();

	    if (response.getStatusCode().isError()) {
	        return "redirect:/noteError";
	    }

	    webClient.post()
	            .uri(apiBaseUrl + "/notes")
	            .headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
	            .body(Mono.just(noteDto), NotesDetailsDto.class)
	            .retrieve()
	            .bodyToMono(NotesDetailsDto.class)
	            .block();

	    model.addAttribute("patId", noteDto.getPatId());

	    return String.format("redirect:/history/%s?patId=%s&patient=%s", noteDto.getPatId(), noteDto.getPatId(), noteDto.getPatient());
	}

	
}
