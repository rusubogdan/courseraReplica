package com.courserareplica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Course {

    @Id
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private Set<Chapter> chapters;
}
