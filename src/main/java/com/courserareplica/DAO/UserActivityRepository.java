package com.courserareplica.DAO;

import com.courserareplica.model.UserActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {

    List<UserActivity> findAll();

    List<UserActivity> findBy(String userId);

    List<UserActivity> findBy(String userId, Integer chapterId);

    List<UserActivity> findBy(String userId, Integer chapterId, Integer paragraphId);
}
