package com.courserareplica.DTO;

import lombok.Data;

@Data
public class CompleteParagraphRequestParameters {
    private Long courseId;
    private Long chapterId;
    private Long paragraphId;
    private String userId;
}
