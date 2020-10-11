package com.mtrepka.polsourcetask.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private int version;
    @Column(columnDefinition = "boolean default false")
    private boolean removed;
    @NonNull
    private String title;
    @NonNull
    private String content;
    @UpdateTimestamp
    private Date modified;
    private Date created;
}
