package com.courserareplica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Course implements Serializable {

    @Id
    @SequenceGenerator(name = "course_seq", sequenceName = "course_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @Setter
    @Getter
    private Long id;

    @Column
    @Setter
    @Getter
    private String name;

    @Column
    @Setter
    @Getter
    private String description;

    // identify by account.getHref()
    @Column
    @Getter
    @Setter
    private String ownerId;

    @Transient
    @Getter
    @Setter
    private Integer percentage; // keep it simple

    @OneToMany(mappedBy = "course")
    @OrderBy("position ASC")
    @Setter
    @Getter
    @JsonManagedReference
    private List<Chapter> chapters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (chapters != null ? !chapters.equals(course.chapters) : course.chapters != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (ownerId != null ? !ownerId.equals(course.ownerId) : course.ownerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (chapters != null ? chapters.hashCode() : 0);
        return result;
    }
}
