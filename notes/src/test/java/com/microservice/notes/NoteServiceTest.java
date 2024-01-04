package com.microservice.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservice.notes.model.Note;
import com.microservice.notes.repository.NoteRepository;
import com.microservice.notes.service.NoteService;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

	@Mock
	private NoteRepository noteRepository;

	@InjectMocks
	private NoteService noteService;

	@BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void getAllNotes() {
		// Mocking behavior of the repository
		List<Note> expectedNotes = Collections.singletonList(new Note("1", "pat1", "Patient 1 note"));
		when(noteRepository.findAll()).thenReturn(expectedNotes);

		List<Note> notes = noteService.getAllNotes();

		assertEquals(expectedNotes, notes);
	}

	@Test
	public void getNoteById() {
		// Mocking behavior of the repository
		Note expectedNote = new Note("1", "pat1", "Patient 1 note");
		when(noteRepository.findById("1")).thenReturn(java.util.Optional.of(expectedNote));

		// Extracting the value from Optional
		Note note = noteService.getNoteById("1").orElseThrow(() -> new RuntimeException("Note not found"));

		assertEquals(expectedNote, note);
	}

	@Test
	public void deleteNoteById() {
		// Mocking behavior of the repository
		String noteIdToDelete = "1";

		// Calling the service method
		noteService.deleteNoteById(noteIdToDelete);

		// Verify that deleteById method was called once with the correct parameter
		verify(noteRepository, times(1)).deleteById(noteIdToDelete);
	}

	@Test
	public void saveNote() {
		// Mocking behavior of the repository
		Note noteToSave = new Note("pat1", "Patient 1", "New note");

		noteService.saveNote(noteToSave);

		// Verifying that save method was called
		verify(noteRepository, times(1)).save(noteToSave);
	}
}
