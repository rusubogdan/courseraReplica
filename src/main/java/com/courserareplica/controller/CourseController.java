package com.courserareplica.controller;

import com.courserareplica.model.Course;
import com.courserareplica.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping
    public String courses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "courses";
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createCourse() {

        return "courses";
    }
}
