package com.courserareplica.service;

import com.courserareplica.model.UserActivity;

import java.util.List;

public interface UserActivityService {

    List<UserActivity> findAll();

    UserActivity save(UserActivity userActivity);

    List<UserActivity> findByUserId(String userId);

    List<UserActivity> findByUserIdAndChapterId(String userId, Long chapterId);

    List<UserActivity> findByUserIdAndChapterIdAndParagraphId(String userId, Long chapterId, Long paragraphId);
}
