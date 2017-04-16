package com.courserareplica.service;

import com.courserareplica.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course getCourse(Long id);

    Course getCourse(String name);

    Course save(Course course);

    List<Course> getUserCourses();
}
