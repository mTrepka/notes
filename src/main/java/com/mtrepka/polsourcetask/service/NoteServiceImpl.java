package com.mtrepka.polsourcetask.service;

import com.mtrepka.polsourcetask.domain.Note;
import com.mtrepka.polsourcetask.domain.NoteArchive;
import com.mtrepka.polsourcetask.repository.NoteArchiveRepository;
import com.mtrepka.polsourcetask.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteArchiveRepository noteArchiveRepository;

    @Override
    public boolean createNote(Note n) {
        n.setCreated(Date.valueOf(LocalDate.now()));
        n.setModified(Date.valueOf(LocalDate.now()));
        n.setVersion(1);
        noteRepository.saveAndFlush(n);
        return true;
    }

    @Override
    public boolean deleteNote(long id) {
        var n = noteRepository.findById(id);
        if (n.isPresent()) {
            n.get().setRemoved(true);
            noteRepository.saveAndFlush(n.get());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateNote(Note n) {
        Note note = noteRepository.getOne(n.getId());
        noteArchiveRepository.save(NoteArchive.createArchive(note));
        note.setVersion(note.getVersion() + 1);
        note.setModified(Date.valueOf(LocalDate.now()));
        note.setContent(n.getContent());
        note.setTitle(n.getTitle());
        noteRepository.save(note);
        return true;
    }

    @Override
    public List<Note> getNoteHistory(Long id) {
        var note = noteRepository.findById(id);
        if (note.isPresent()) {
            List<NoteArchive> notes = noteArchiveRepository.findAllByNoteId(id);
            var result = new ArrayList<Note>();
            result.add(note.get());
            notes.stream().map(NoteArchive::toNote).forEach(result::add);
            return result;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll().stream().filter(e -> !e.isRemoved()).collect(Collectors.toList());
    }

    @Override
    public Optional<Note> getNote(long id) {
        Note n = noteRepository.findById(id).get();
        return n.isRemoved() ? Optional.empty() : Optional.of(n);
    }
}
