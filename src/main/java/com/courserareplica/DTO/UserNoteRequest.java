package com.courserareplica.DTO;

import lombok.Data;

@Data
public class UserNoteRequest {
    private String userId;
    private Long paragraphId;
    private String note;
}
