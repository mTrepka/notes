package com.mtrepka.polsourcetask.repository;


import com.mtrepka.polsourcetask.domain.NoteArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteArchiveRepository extends JpaRepository<NoteArchive, Long> {
    List<NoteArchive> findAllByNoteId(Long noteId);
}
