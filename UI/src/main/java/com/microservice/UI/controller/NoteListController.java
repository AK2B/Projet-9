package com.microservice.UI.controller;
import java.util.List;

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
	
	@GetMapping("/{patientId}")
	public String listPatient(@PathVariable String patientId, Model model) {
		List<NotesDetailsDto> notes = webClient.get()
				.uri("http://localhost:8081/notes/{id}", patientId)
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
				.retrieve()
				.bodyToFlux(NotesDetailsDto.class)
				.collectList()
				.block();

		model.addAttribute("notes", notes);
		
		Risk risk = webClient.get()				
				.uri("http://localhost:8081/assessment/{id}", patientId)
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
		
		webClient.post()				
                .uri("http://localhost:8081/notes")
				.headers(httpHeaders -> httpHeaders.setBasicAuth("user", "password"))
                .body(Mono.just(noteDto), NotesDetailsDto.class)
                .retrieve()
                .bodyToMono(NotesDetailsDto.class)
                .block();

        model.addAttribute("patId", noteDto.getPatId());

        return String.format("redirect:/history/%s", noteDto.getPatId());
    }
	
}
