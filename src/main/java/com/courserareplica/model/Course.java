package com.courserareplica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Course {

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

    @Column
    @Getter
    @Setter
    private Long ownerId;

    @OneToMany(mappedBy = "course")
    @OrderBy("position ASC")
    @Setter
    @Getter
    private List<Chapter> chapters;
}
