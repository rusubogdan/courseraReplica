package com.courserareplica.service;

import com.courserareplica.model.UserNote;

import java.util.List;

public interface UserNoteService {

    UserNote findOne(Long id);

    UserNote save(UserNote userNote);

    void delete(UserNote userNote);

    List<UserNote> findByParagraphIdAndUserId(Long paragraphId, String userId);

    List<UserNote> findByUserId(String userId);
}
