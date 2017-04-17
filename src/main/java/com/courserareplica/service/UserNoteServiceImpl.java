package com.courserareplica.service;

import com.courserareplica.DAO.UserNoteDAO;
import com.courserareplica.model.UserNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserNoteServiceImpl implements UserNoteService {

    @Autowired
    private UserNoteDAO userNoteDAO;

    @Override
    public UserNote findOne(Long id) {
        return userNoteDAO.findOne(id);
    }

    @Override
    public UserNote save(UserNote userNote) {
        return userNoteDAO.save(userNote);
    }

    @Override
    public void delete(UserNote userNote) {
        userNoteDAO.delete(userNote);
    }

    @Override
    public List<UserNote> findByParagraphIdAndUserId(Long paragraphId, String userId) {
        return userNoteDAO.findByParagraphIdAndUserId(paragraphId, userId);
    }

    @Override
    public List<UserNote> findByUserId(String userId) {
        return userNoteDAO.findByUserId(userId);
    }
}
