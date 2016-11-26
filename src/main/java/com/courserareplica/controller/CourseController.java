package com.courserareplica.controller;

import com.courserareplica.model.Course;
import com.courserareplica.service.CourseService;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/ajax/saveCourse", method = RequestMethod.POST)
    @ResponseBody
    public String saveCourse(
            @RequestParam(value = "courseTitle") String courseTitle,
            @RequestParam(value = "courseDescription") String courseDescription) {

        // TODO validate not null and return message

        // course title is unique
        Course course = courseService.getCourse(courseTitle);
        if (course != null) {
            // update it's description
            if (!course.getDescription().equals(courseDescription)) {
                course.setDescription(courseDescription);
                courseService.save(course);
            }

            // todo return dto
            return courseTitle;
        }

        course = new Course();
        course.setName(courseTitle);
        course.setDescription(courseDescription);

//        AccountResolver.INSTANCE.getAccount()

        // todo auth user id
        course.setOwnerId(1L);

        courseService.save(course);

        // todo return course DTO
        // todo return on course's page - in order to add there chapters and others
        return courseTitle;
    }

    // todo might be added and the title in the link: id - title, then extract those
    @RequestMapping(value = "/{courseId}")
    public String coursePage(@PathParam(value = "courseId") Long courseId,
                             Model model) {

        Course course = courseService.getCourse(courseId);

        if (course == null) {
            // todo with message
            return "redirect:/courses";
        }

        model.addAttribute("course", course);

        return "courseView";
    }

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
    
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public String viewCourse(Model model) {
    	model.addAttribute("activeNavButton", "course");
    	model.addAttribute("course.title", "Cursul exemplu");
    	model.addAttribute("course.author", "Autor");
        return "course";
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.GET)
    public String test() {

        return "addCourse";
    }

    @RequestMapping(value = "/testit", method = RequestMethod.POST)
    public String testCourse(@RequestParam String body, Model model) {
        model.addAttribute("success", true);

        return "courses";
    }
}
