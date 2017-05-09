package com.courserareplica.service;

import com.courserareplica.DAO.ChapterRepository;
import com.courserareplica.DAO.CourseRepository;
import com.courserareplica.DAO.UserActivityRepository;
import com.courserareplica.model.Chapter;
import com.courserareplica.model.Course;
import com.courserareplica.model.Paragraph;
import com.courserareplica.model.UserActivity;
import com.stormpath.sdk.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public UserActivity save(UserActivity userActivity) {
        UserActivity savedUserActivity = null;

        try {
            List list = userActivityRepository.findByUserIdAndChapterIdAndParagraphId
                    (userActivity.getUserId(), userActivity.getChapterId(), userActivity.getParagraphId());
            if (list != null && list.isEmpty()) {
                // save a new activity
                savedUserActivity = userActivityRepository.save(userActivity);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());

            return null;
        }

        return savedUserActivity;
    }

    @Override
    public List<UserActivity> findAll() {
        return userActivityRepository.findAll();
    }

    @Override
    public List<UserActivity> findByUserId(String userId) {
        return userActivityRepository.findByUserId(userId);
    }

    @Override
    public List<UserActivity> findByUserIdAndChapterId(String userId, Long chapterId) {
        return userActivityRepository.findByUserIdAndChapterId(userId, chapterId);
    }

    @Override
    public List<UserActivity> findByUserIdAndChapterIdAndParagraphId(String userId, Long chapterId, Long paragraphId) {
        return userActivityRepository.findByUserIdAndChapterIdAndParagraphId(userId, chapterId, paragraphId);
    }

    @Override
    public List<Course> getUserCoursesWithPercentages(Account account) {
        // use the Stormpath's account href for user id
        List<Course> courses = new ArrayList<>();

        try {
            List<UserActivity> activities = userActivityRepository.findByUserId(account.getHref());


            // stream -> courseId
            // set to remove duplicates
            Set<Long> courseIds = activities.stream()
                    .map(UserActivity::getCourseId)
                    .collect(Collectors.toSet());

            for (Long courseId : courseIds) {
                Course course = courseRepository.findOne(courseId);
                List<Chapter> chapters = course.getChapters();

                int noOfTotalParagraphs = 0;
                for (Chapter chapter : chapters) {
                    List<Paragraph> paragraphs = chapter.getParagraphs();

                    if (paragraphs != null && !paragraphs.isEmpty()) {
                        noOfTotalParagraphs += paragraphs.size();
                    }
                }

                List<UserActivity> userActivities = userActivityRepository.findByUserIdAndCourseId(account.getHref(), courseId);
                int noOfUserParagraphs = (userActivities != null && !userActivities.isEmpty()) ? userActivities.size() : 0;

                int percentage = new Double(Math.floor((((float) noOfUserParagraphs / noOfTotalParagraphs) * 100))).intValue();
                if (percentage >= 100) {
                    percentage = 100;
                }

                course.setPercentage(percentage);

                courses.add(course);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (courses.isEmpty()) {
            Course mockCourse = new Course();
            mockCourse.setName("Nici un curs");
            mockCourse.setPercentage(0);

            courses.add(mockCourse);
        }

        return courses;
    }

    @Override
    public List<Course> getUserCourses(Account account) {
        // use the Stormpath's account href for user id
        List<Course> courses = new ArrayList<>();

        try {
            List<UserActivity> activities = userActivityRepository.findByUserId(account.getHref());

            // stream -> courseId
            // set to remove duplicates
            Set<Long> courseIds = activities.stream()
                    .map(UserActivity::getCourseId)
                    .collect(Collectors.toSet());

            for (Long courseId : courseIds) {
                Course course = courseRepository.findOne(courseId);
                courses.add(course);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (courses.isEmpty()) {
            Course mockCourse = new Course();
            mockCourse.setName("Nici un curs");
            mockCourse.setPercentage(0);

            courses.add(mockCourse);
        }

        return courses;
    }
}

/*
// use the Stormpath's account href for user id
        List<Course> courses = new ArrayList<>();

        try {
            List<UserActivity> activities = userActivityRepository.findByUserId(account.getHref());

            // stream -> courseId
            // set to remove duplicates
            Set<Long> courseIds = activities.stream()
                    .map(UserActivity::getCourseId)
                    .collect(Collectors.toSet());

            for (Long courseId : courseIds) {
                Course course = courseRepository.findOne(courseId);
                courses.add(course);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (courses.isEmpty()) {
            Course mockCourse = new Course();
            mockCourse.setName("Nici un curs");
            mockCourse.setPercentage(0);

            courses.add(mockCourse);
        }

        return courses;
 */