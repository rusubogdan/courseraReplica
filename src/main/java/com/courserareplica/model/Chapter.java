package com.courserareplica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Chapter implements Serializable {

    @Id
    @Column
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
}
