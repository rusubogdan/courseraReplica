package com.courserareplica.controller;

import com.courserareplica.DTO.EditTitleParams;
import com.courserareplica.model.*;
import com.courserareplica.service.*;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ObjectUtils.isEmpty;


@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private UserNoteService userNoteService;

    @RequestMapping
    public String courses(Model model, ServletRequest request) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        Account account = AccountResolver.INSTANCE.getAccount(request);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

        return "courses";
    }

    @RequestMapping(value = "/myCourses", method = RequestMethod.GET)
    public String myCourses(ServletRequest request, Model model) {
        Account account = AccountResolver.INSTANCE.getAccount(request);

        List<Course> userCourses = userActivityService.getUserCoursesWithPercentages(account);
        model.addAttribute("userCourses", userCourses);

        return "myCourses";
    }

    @RequestMapping(value = "/ajax/saveCourse", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    @ResponseBody
    public String saveCourse(
            @RequestParam(value = "courseTitle") String courseTitle,
            @RequestParam(value = "courseDescription") String courseDescription,
            ServletRequest request) {

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
            return course.getId().toString();
        }

        course = new Course();
        course.setName(courseTitle);
        course.setDescription(courseDescription);

        Account account = AccountResolver.INSTANCE.getAccount(request);

        if (account == null) {
            return null;
        }

        course.setOwnerId(account.getHref());

        courseService.save(course);

        // todo return course DTO
        // todo return on course's page - in order to add there chapters and others
        return course.getId().toString();
    }

    // todo might be added and the title in the link: id - title, then extract those
    @RequestMapping(value = "/{courseId}")
    public String coursePage(@PathVariable(value = "courseId") Long courseId,
                             @RequestParam(required = false, defaultValue = "0") Long chapter,
                             ServletRequest request,
                             Model model) {
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            // todo with message
            return "redirect:/courses";
        }

        // get chapters for course

        Account account = AccountResolver.INSTANCE.getAccount(request);

        if (account == null) {
            return "redirect:/courses";
        }

        model.addAttribute("course", course);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

        return "course";
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}/newParagraph", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map addNewChapter(@PathVariable Long courseId,
                             @PathVariable Long chapterId,
                             @RequestBody Paragraph paragraph) {
        Map response = new HashMap<>();
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            response.put("error", true);
            return response;
        }

        Chapter chapter = chapterService.findBy(chapterId);
        if (chapter == null) {
            response.put("error", true);
            return response;
        }

        paragraph.setChapter(chapter);

        List<Paragraph> paragraphs = chapter.getParagraphs();
        Integer lastPosition = (paragraphs == null || paragraphs.isEmpty()) ? 0 : paragraphs.get(paragraphs.size() - 1).getPosition() + 1;
        paragraph.setPosition(lastPosition);

        paragraphService.save(paragraph);

        response.put("success", true);

        return response;
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}/slideshow")
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public String slideshowChapter(@PathVariable Long courseId,
                                   @PathVariable Long chapterId,
                                   Model model) {

        model.addAttribute("course", courseService.getCourse(courseId));
        model.addAttribute("chapter", chapterService.findBy(chapterId));

        return "chapterSlideShow";
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}/par/{paragraphId}/edit", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map editParagraph(@PathVariable Long courseId,
                             @PathVariable Long chapterId,
                             @PathVariable Long paragraphId,
                             @RequestBody String paragraphText) {
        Map response = new HashMap<>();
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            response.put("error", true);
            return response;
        }

        Chapter chapter = chapterService.findBy(chapterId);
        if (chapter == null) {
            response.put("error", true);
            return response;
        }

        Paragraph paragraph = paragraphService.findBy(paragraphId);
        if (paragraph == null) {
            response.put("error", true);
            return response;
        }

        paragraph.setText(paragraphText);
        paragraphService.save(paragraph);

        response.put("success", true);

        return response;
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}/par/{paragraphId}/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map deleteParagraph(@PathVariable Long courseId,
                               @PathVariable Long chapterId,
                               @PathVariable Long paragraphId) {
        Map response = new HashMap<>();
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            response.put("error", true);
            return response;
        }

        Chapter chapter = chapterService.findBy(chapterId);
        if (chapter == null) {
            response.put("error", true);
            return response;
        }

        Paragraph paragraph = paragraphService.findBy(paragraphId);
        if (paragraph == null) {
            response.put("error", true);
            return response;
        }

        paragraphService.delete(paragraph);

        response.put("success", true);

        return response;
    }

    @RequestMapping(value = "/{courseId}/edit/do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map courseEdit(@PathVariable Long courseId,
                          @RequestBody Course course) {

        Map<String, Object> response = new HashMap<>();
        Course courseObj = courseService.getCourse(courseId);

        if (courseObj == null) {
            response.put("success", false);
            response.put("errorMessage", "course id is invalid");
            return response;
        }

        // update
        boolean isUpdated = false;
        if (!isEmpty(course.getName()) && !course.getName().equals(courseObj.getName())) {
            isUpdated = true;
            courseObj.setName(course.getName());
        }

        if (!isEmpty(course.getDescription()) && !course.getDescription().equals(courseObj.getDescription())) {
            isUpdated = true;
            courseObj.setDescription(course.getDescription());
        }

        if (isUpdated) {
            courseService.save(courseObj);
        }

        response.put("success", true);

        return response;
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}")
    public String chapterPage(@PathVariable Long courseId,
                              @PathVariable Long chapterId,
                              ServletRequest request,
                              Model model) {
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            return "redirect:/courses";
        }

        Chapter chapter = chapterService.findBy(chapterId);

        if (chapter == null) {
            return "redirect:/courses";
        } else if (!course.getChapters().contains(chapter)) {
            return "redirect:/courses";
        }

        Account account = AccountResolver.INSTANCE.getAccount(request);

        if (account == null) {
            return "redirect:/courses";
        }

        // for each paragraph, set if it was viewed by this user and if it has a note
        for (Paragraph paragraph : chapter.getParagraphs()) {
            // set activity
            List<UserActivity> activities = userActivityService.findByUserIdAndChapterIdAndParagraphId(account.getHref(), chapter.getId(), paragraph.getId());
            paragraph.setCompleted((activities != null && !activities.isEmpty()));

            // set user notes
            List<UserNote> userNotes = userNoteService.findByParagraphIdAndUserId(paragraph.getId(), account.getHref());
            if (userNotes != null && !userNotes.isEmpty()) {
                UserNote note = userNotes.get(0);

                if (note != null) {
                    paragraph.setNote(note.getNote());
                }
            }
        }

        model.addAttribute("course", course);
        model.addAttribute("chapter", chapter);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

        return "chapterView";
    }


    // todo might be added and the title in the link: id - title, then extract those
    @RequestMapping(value = "/{courseId}/edit")
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public String courseEditPage(@PathVariable(value = "courseId") Long courseId,
                                 @RequestParam(required = false, defaultValue = "0") Long chapter,
                                 Model model) {
        Course course = courseService.getCourse(courseId);

        if (course == null) {
            // todo with message
            return "redirect:/courses";
        }

        // get chapter for course

        model.addAttribute("course", course);

        return "courseEdit";
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public String addCourse(ServletRequest request, Model model) {

        Account account = AccountResolver.INSTANCE.getAccount(request);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

        return "addCourse";
    }


    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    @RequestMapping(value = "/{courseId}/ch/newChapter", method = RequestMethod.GET)
    public String addNewChapter(@PathVariable Long courseId,
                                Model model, ServletRequest request) {

        Account account = AccountResolver.INSTANCE.getAccount(request);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

        Course course = courseService.getCourse(courseId);

        if (course == null) {
            model.addAttribute("error", "Course doesn't exist");

            return "redirect:/courses";
        }

        model.addAttribute("course", course);

        return "courseNewChapter";
    }

    @RequestMapping(value = "/{courseId}/ch/{chapterId}/editTitleAndDesc", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map editChapterTitleDescr(@PathVariable Long courseId,
                                     @PathVariable Long chapterId,
                                     @RequestBody EditTitleParams editTitleParams) {
        Map<String, Object> response = new HashMap<>();

        Course course = courseService.getCourse(courseId);

        if (course == null) {
            response.put("success", false);

            return response;
        }

        Chapter chapter = chapterService.findBy(chapterId);

        if (chapter == null) {
            response.put("success", false);

            return response;
        }

        if (editTitleParams.getTitle().length() >= 5) {
            chapter.setName(editTitleParams.getTitle());
        }

        if (editTitleParams.getDescription().length() >= 5) {
            chapter.setDescription(editTitleParams.getDescription());
        }

        chapterService.save(chapter);

        response.put("success", true);

        return response;
    }


    @RequestMapping(value = "/{courseId}/ch/newChapter/do", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PreAuthorize("hasAuthority('https://api.stormpath.com/v1/groups/46mjLnyZ3isSurwQIPKBng')")
    public Map newChapterDo(@PathVariable Long courseId,
                            @RequestBody Chapter chapter) {
        Course existingCourse = courseService.getCourse(courseId);
        Map<String, Object> result = new HashMap<>();

        if (existingCourse == null) {
            result.put("error", true);
            result.put("cause", "course doesn't exist");

            return result;
        }

        // if unique course - chapter
        // TODO by name and course.name

        if (chapter.getName().equals("")) {
            result.put("error", true);
            result.put("cause", "empty chapter name");

            return result;
        }

        // save this chapter
        chapter.setPosition(existingCourse.getChapters().size() + 1);
        chapterService.save(chapter);
        chapter.setCourse(existingCourse);
        chapterService.save(chapter);

        result.put("result", existingCourse);
        result.put("success", true);

        return result;
    }
}
