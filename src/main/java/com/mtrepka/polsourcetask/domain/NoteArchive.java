package com.mtrepka.polsourcetask.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class NoteArchive {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private int version;
    private String title;
    private String content;
    private Date modified;
    private Date created;
    private long noteId;

    public static NoteArchive createArchive(Note n) {
        NoteArchive result = new NoteArchive();
        result.version = n.getVersion();
        result.title = n.getTitle();
        result.content = n.getContent();
        result.modified = n.getModified();
        result.created = n.getCreated();
        result.noteId = n.getId();
        return result;
    }

    public static Note toNote(NoteArchive n) {
        Note result = new Note();
        result.setVersion(n.getVersion());
        result.setTitle(n.getTitle());
        result.setContent(n.getContent());
        result.setModified(n.getModified());
        result.setCreated(n.getCreated());
        result.setId(n.getNoteId());
        return result;
    }
}
