package com.microservice.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservice.notes.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

}

