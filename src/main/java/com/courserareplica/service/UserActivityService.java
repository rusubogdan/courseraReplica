package com.courserareplica.service;

import com.courserareplica.model.Course;
import com.courserareplica.model.UserActivity;
import com.stormpath.sdk.account.Account;

import java.util.List;

public interface UserActivityService {

    List<UserActivity> findAll();

    UserActivity save(UserActivity userActivity);

    List<UserActivity> findByUserId(String userId);

    List<UserActivity> findByUserIdAndChapterId(String userId, Long chapterId);

    List<UserActivity> findByUserIdAndChapterIdAndParagraphId(String userId, Long chapterId, Long paragraphId);

    List<Course> getUserCourses(Account account);


}
