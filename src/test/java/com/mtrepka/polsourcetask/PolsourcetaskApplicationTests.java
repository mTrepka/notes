package com.mtrepka.polsourcetask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtrepka.polsourcetask.domain.Note;
import com.mtrepka.polsourcetask.repository.NoteArchiveRepository;
import com.mtrepka.polsourcetask.repository.NoteRepository;
import com.mtrepka.polsourcetask.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PolsourcetaskApplication.class)
class PolsourcetaskApplicationTests {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private NoteArchiveRepository noteArchiveRepository;
    @Autowired
    private NoteService noteService;
    @Autowired
    private MockMvc mvc;



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findAllTest() throws Exception {
        List<Note> testNote = noteRepository.findAll();
        MvcResult result = mvc.perform(get("/note/all/")).andExpect(status().isOk()).andReturn();
        String data = result.getResponse().getContentAsString();
        List<Note> notes = new ObjectMapper().readValue(data, new TypeReference<>() {
        });
        assertEquals(testNote.toString(), notes.toString());
    }

    @Test
    void postTest() throws Exception {
        Note n = new Note();
        n.setContent("kontekt");
        n.setTitle("tytui");
        mvc.perform(post("/note/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(n)))
                .andExpect(status()
                        .isCreated());
        assertTrue(noteRepository.findByTitle("tytui").isPresent());

        Note n1 = new Note();
        n1.setContent("kontekt");
        mvc.perform(post("/note/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(n1)))
                .andExpect(status()
                        .isBadRequest());

        Note n2 = new Note();
        n2.setTitle("tytui");
        mvc.perform(post("/note/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(n2)))
                .andExpect(status()
                        .isBadRequest());

        Note n3 = new Note();
        mvc.perform(post("/note/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(n3)))
                .andExpect(status()
                        .isBadRequest());
    }

    @Test
    void getTest() throws Exception {
        Note n = new Note();
        n.setContent("kontekt");
        n.setTitle("tytuiTest123");
        noteService.createNote(n);

        MvcResult result = mvc.perform(get("/note/" + n.getId()))
                .andExpect(status().isOk()).andReturn();
        String res = result.getResponse().getContentAsString();
        Note b = new ObjectMapper().readValue(res, Note.class);
        assertEquals(n.toString(), b.toString());
    }

    @Test
    void putTest() throws Exception {
        Note n = new Note();
        n.setContent("kontekt");
        n.setTitle("title");
        noteService.createNote(n);
        n.setContent("mis");
        mvc.perform(put("/note/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(n)))
                .andExpect(status().isOk()).andReturn();
        Note b = noteService.getNote(n.getId()).get();
        assertEquals(b.getVersion(), n.getVersion() + 1);
        assertEquals("mis", b.getContent());

    }

    @Test
    void deleteTest() throws Exception {
        Note n = new Note();
        String title = "tytuiTest12355555";
        n.setContent("kontekt");
        n.setTitle("title");
        noteService.createNote(n);
        //check if exists in all
        MvcResult result = mvc.perform(get("/note/all/")).andExpect(status().isOk()).andReturn();
        String data = result.getResponse().getContentAsString();
        List<Note> notes = new ObjectMapper().readValue(data, new TypeReference<>() {
        });
        assertTrue(notes.stream().anyMatch(e -> e.getId() == n.getId()));

        //delete note
        mvc.perform(delete("/note/" + n.getId()))
                .andExpect(status().isOk());
        // check if exists by id
        mvc.perform(get("/note/" + n.getId()))
                .andExpect(status().isNotFound());

        //check existence in database
        assertTrue(noteRepository.findById(n.getId()).isPresent());

        //check if exists in all
        result = mvc.perform(get("/note/all/")).andExpect(status().isOk()).andReturn();
        data = result.getResponse().getContentAsString();
        notes = new ObjectMapper().readValue(data, new TypeReference<>() {
        });
        assertFalse(notes.stream().anyMatch(e -> e.getId() == n.getId()));
    }

    @Test
    void getNoteHistory() throws Exception {
        Note testNote = new Note();
        testNote.setTitle("Testowa");
        testNote.setContent("Pierwsza");
        noteService.createNote(testNote);

        testNote.setContent("Dwa");
        noteService.updateNote(testNote);

        testNote.setContent("Trzy");
        noteService.updateNote(testNote);

        MvcResult result = mvc.perform(get("/note/history/" + testNote.getId())).andExpect(status().isOk()).andReturn();
        String data = result.getResponse().getContentAsString();
        List<Note> notes = new ObjectMapper().readValue(data, new TypeReference<>() {
        });

        assertEquals(notes.size(), 3);
        Note second = notes.stream().filter(e -> e.getContent().equals("Dwa")).findAny().get();
        Note third = notes.stream().filter(e -> e.getContent().equals("Trzy")).findAny().get();
        assertEquals(testNote.getId(), second.getId());
        assertEquals(testNote.getId(), third.getId());
        assertTrue(notes.stream().allMatch(e -> e.getTitle().equals("Testowa")));

    }
}
