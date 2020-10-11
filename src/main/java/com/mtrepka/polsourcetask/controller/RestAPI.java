package com.mtrepka.polsourcetask.controller;

import com.mtrepka.polsourcetask.domain.Note;
import com.mtrepka.polsourcetask.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestAPI {
    private final NoteService noteService;

    @GetMapping("/note/all/")
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping("/note/")
    public ResponseEntity postNote(@RequestBody Note note) {
        return new ResponseEntity(noteService.createNote(note) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/note/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        var note = noteService.getNote(Long.parseLong(id));
        if (note.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(note.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteById(@PathVariable("id") String id) {
        return new ResponseEntity(noteService.deleteNote(Long.parseLong(id)) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/note/")
    public ResponseEntity updateNote(@RequestBody Note note) {
        return new ResponseEntity(noteService.updateNote(note) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/note/history/{id}")
    public List<Note> getNoteHistory(@PathVariable("id") String id) {
        return noteService.getNoteHistory(Long.parseLong(id));
    }
}
