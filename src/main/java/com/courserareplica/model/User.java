package com.courserareplica.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @Setter
    @Getter
    private Long id;

    @Column
    @Setter
    @Getter
    private String email;

    @Column
    @Setter
    @Getter
    private String password;
}
