package com.courserareplica.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private Long courseId;

    @Column
    private Long chapterId;

    @Column
    private Long paragraphId;
}
