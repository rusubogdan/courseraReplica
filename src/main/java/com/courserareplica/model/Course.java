package com.courserareplica.model;

import com.stormpath.sdk.account.Account;
import lombok.Getter;
import lombok.Setter;

public class Course {

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Getter
    @Setter
    private Long ownerId;


}
