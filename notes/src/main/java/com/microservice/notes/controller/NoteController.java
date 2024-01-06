package com.microservice.notes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.notes.model.Note;
import com.microservice.notes.service.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {

	private final NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping
	public List<Note> getAllNotes() {
		return noteService.getAllNotes();
	}

	@GetMapping("/{param}")
	public ResponseEntity<?> getNoteByIdOrByPatId(@PathVariable String param) {
	    if (param.length() < 15) {
	        List<Note> notes = noteService.getNotesByPatId(param);
	        return new ResponseEntity<>(notes, HttpStatus.OK);
	    } else {
	        Optional<Note> note = noteService.getNoteById(param);
	        return note.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
	                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	}

	@PostMapping
	public ResponseEntity<Note> saveNote(@RequestBody Note note) {
		Note savedNote = noteService.saveNote(note);
		return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteNoteById(@PathVariable String id) {
		noteService.deleteNoteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
