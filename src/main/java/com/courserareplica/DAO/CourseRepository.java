package com.courserareplica.DAO;

import com.courserareplica.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findAll();

    List<Course> findByName(String name);
}
