package com.courserareplica.service;

import com.courserareplica.DAO.CourseRepository;
import com.courserareplica.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = repository.findAll();

        return courses;
    }

    @Override
    public Course getCourse(String name) {
        List<Course> courses = repository.findByName(name);
        return courses.isEmpty() ? null : courses.get(0);
    }

    @Override
    public Course save(Course course) {
        // some validation
        Course savedCourse = repository.save(course);

        return savedCourse;
    }

	@Override
	public Course getCourse(Long id) {
		return repository.findOne(id);
	}
}
