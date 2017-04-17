package com.courserareplica.DAO;

import com.courserareplica.model.UserNote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserNoteDAO extends CrudRepository<UserNote, Long> {

    UserNote findOne(Long id);

    UserNote save(UserNote userNote);

    void delete(UserNote userNote);

    List<UserNote> findByParagraphIdAndUserId(Long paragraphId, String userId);

    List<UserNote> findByUserId(String userId);
}
