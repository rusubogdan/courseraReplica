package com.courserareplica.DAO;

import com.courserareplica.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    // overridden just to return a java.util.List object
    List<Course> findAll();

	List<Course> findByName(String name);
}
