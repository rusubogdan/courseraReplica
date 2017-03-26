package com.courserareplica.service;

import com.courserareplica.model.UserActivity;

import java.util.List;

public interface UserActivityService {

    List<UserActivity> findAll();

    List<UserActivity> findBy(String userId);

    List<UserActivity> findBy(String userId, Integer chapterId);

    List<UserActivity> findBy(String userId, Integer chapterId, Integer paragraphId);
}
