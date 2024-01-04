package com.microservice.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.microservice.notes.controller.NoteController;
import com.microservice.notes.model.Note;
import com.microservice.notes.service.NoteService;

class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllNotes() {
        // Mocking the behavior of the service
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("1", "pat1", "Patient 1 note"));
        notes.add(new Note("2", "pat2", "Patient 2 note"));
        Mockito.when(noteService.getAllNotes()).thenReturn(notes);

        // Calling the controller method
        List<Note> result = noteController.getAllNotes();

        // Asserting the result
        assertEquals(2, result.size());
    }

    @Test
    void getNoteByIdOrByPatIdWithPatId() {
        // Mocking the behavior of the service
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("1", "pat1", "Patient 1 note"));
        Mockito.when(noteService.getNotesByPatId("pat1")).thenReturn(notes);

        // Calling the controller method
        ResponseEntity<?> result = noteController.getNoteByIdOrByPatId("pat1");

        // Asserting the result
        assertTrue(result.getBody() instanceof List);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(notes, result.getBody());
    }

    @Test
    void saveNote() {
        // Mocking the behavior of the service
        Note noteToSave = new Note("pat1", "Patient 1", "New note");
        Mockito.when(noteService.saveNote(noteToSave)).thenReturn(noteToSave);

        // Calling the controller method
        ResponseEntity<Note> result = noteController.saveNote(noteToSave);

        // Asserting the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("pat1", result.getBody().getPatId());
    }

    @Test
    void deleteNoteById() {
        // Mocking the behavior of the service
        Mockito.doNothing().when(noteService).deleteNoteById("1");

        // Calling the controller method
        ResponseEntity<Void> result = noteController.deleteNoteById("1");

        // Asserting the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}
