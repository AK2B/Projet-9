package com.microservice.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.notes.model.Note;
import com.microservice.notes.repository.NoteRepository;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    
    public List<Note> getNotesByPatId(String patId) {
        return noteRepository.findAllByPatId(patId);
    }

    public Optional<Note> getNoteById(String id) {
        return noteRepository.findById(id);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

}
