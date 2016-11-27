package com.courserareplica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Chapter implements Serializable {

    @Id
    @SequenceGenerator(name = "chapter_seq", sequenceName = "chapter_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapter_seq")
    @Setter
    @Getter
    private Long id;

    @Column
    @Setter
    @Getter
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @Getter
    @Setter
    private Course course;

    /**
     * The position in page ordered starting from 1
     */
    @Column
    @Getter
    @Setter
    private Integer position;

    @Column
    @Setter
    @Getter
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chapter chapter = (Chapter) o;

        if (course != null ? !course.equals(chapter.course) : chapter.course != null) return false;
        if (description != null ? !description.equals(chapter.description) : chapter.description != null) return false;
        if (id != null ? !id.equals(chapter.id) : chapter.id != null) return false;
        if (name != null ? !name.equals(chapter.name) : chapter.name != null) return false;
        if (position != null ? !position.equals(chapter.position) : chapter.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
