package com.courserareplica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table
@Entity
public class Paragraph implements Serializable {

    @Id
    @SequenceGenerator(name = "paragraph_seq", sequenceName = "paragraph_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paragraph_seq")
    @Setter
    @Getter
    private Long id;

    @Column
    @Setter
    @Getter
    private String text;

    @Setter
    @Getter
    @Transient
    private Boolean completed;

    @Column
    @Setter
    @Getter
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    @Getter
    @Setter
    @JsonBackReference
    private Chapter chapter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paragraph paragraph = (Paragraph) o;

        if (chapter != null ? !chapter.equals(paragraph.chapter) : paragraph.chapter != null) return false;
        if (id != null ? !id.equals(paragraph.id) : paragraph.id != null) return false;
        if (text != null ? !text.equals(paragraph.text) : paragraph.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (chapter != null ? chapter.hashCode() : 0);
        return result;
    }
}
