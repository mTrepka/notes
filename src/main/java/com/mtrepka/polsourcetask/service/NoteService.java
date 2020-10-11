package com.mtrepka.polsourcetask.service;

import com.mtrepka.polsourcetask.domain.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    boolean createNote(Note n);

    boolean deleteNote(long id);

    boolean updateNote(Note n);

    Optional<Note> getNote(long id);

    List<Note> getNoteHistory(Long id);

    List<Note> getAllNotes();

}
