package com.courserareplica.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Data
public class UserNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private Long paragraphId;

    @Column
    private String note;
}
