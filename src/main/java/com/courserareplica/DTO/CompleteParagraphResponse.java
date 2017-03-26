package com.courserareplica.DTO;

import lombok.Data;

@Data
public class CompleteParagraphResponse {
    private Boolean success;
    private String error;

    public CompleteParagraphResponse(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
