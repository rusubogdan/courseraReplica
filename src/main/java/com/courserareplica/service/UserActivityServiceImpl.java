package com.courserareplica.service;

import com.courserareplica.DAO.UserActivityRepository;
import com.courserareplica.model.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityRepository repository;

    @Override
    public List<UserActivity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<UserActivity> findBy(String userId) {
        return repository.findBy(userId);
    }

    @Override
    public List<UserActivity> findBy(String userId, Integer chapterId) {
        return repository.findBy(userId, chapterId);
    }

    @Override
    public List<UserActivity> findBy(String userId, Integer chapterId, Integer paragraphId) {
        return repository.findBy(userId, chapterId, paragraphId);
    }
}
