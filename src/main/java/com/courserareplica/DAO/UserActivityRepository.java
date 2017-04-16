package com.courserareplica.DAO;

import com.courserareplica.model.UserActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {

    List<UserActivity> findAll();

    UserActivity save(UserActivity userActivity);

    List<UserActivity> findByUserId(String userId);

    List<UserActivity> findByUserIdAndChapterId(String userId, Long chapterId);

    List<UserActivity> findByUserIdAndChapterIdAndParagraphId(String userId, Long chapterId, Long paragraphId);

    List<UserActivity> findByUserIdAndCourseId(String userId, Long courseId);
}
