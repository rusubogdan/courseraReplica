package com.courserareplica.controller;


import com.courserareplica.DTO.CompleteParagraphRequestParameters;
import com.courserareplica.DTO.CompleteParagraphResponse;
import com.courserareplica.DTO.UserNoteRequest;
import com.courserareplica.DTO.UserNoteResponse;
import com.courserareplica.model.UserActivity;
import com.courserareplica.model.UserNote;
import com.courserareplica.service.UserActivityService;
import com.courserareplica.service.UserNoteService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/v1/ajax")
public class UserActionController {

    @Autowired
    private UserActivityService userActivityService;

    @Autowired
    private AccountResolver accountResolver;

    @Autowired
    private UserNoteService userNoteService;

    @RequestMapping(value = "/userNote/saveUserNote", method = RequestMethod.POST)
    @ResponseBody
    public UserNoteResponse saveUserNote(@RequestBody UserNoteRequest userNoteRequest,
                               ServletRequest request) {
        UserNoteResponse response = new UserNoteResponse();
        Account account = accountResolver.getAccount(request);

        UserNote userNote;

        // allow one user note per paragraph
        List<UserNote> userNotes = userNoteService.findByParagraphIdAndUserId(userNoteRequest.getParagraphId(), account.getHref());

        if (userNotes != null && !userNotes.isEmpty()) {
            // update the note
            userNote = userNotes.get(0);
            userNote.setNote(userNoteRequest.getNote());
        } else {
            // create new note
            userNote = new UserNote();
            userNote.setParagraphId(userNoteRequest.getParagraphId());
            userNote.setUserId(account.getHref());
            userNote.setNote(userNoteRequest.getNote());
        }

        try {
            // save or update
            userNoteService.save(userNote);
            response.setError(false);
        } catch (Exception e) {
            response.setError(true);
        }

        return response;
    }

    @RequestMapping(value = "/userNote/deleteUserNote", method = RequestMethod.POST)
    @ResponseBody
    public UserNoteResponse deleteUserNote(@RequestBody UserNoteRequest userNoteRequest,
                                         ServletRequest request) {
        UserNoteResponse response = new UserNoteResponse();
        Account account = accountResolver.getAccount(request);

        try {
            List<UserNote> userNotes = userNoteService.findByParagraphIdAndUserId(userNoteRequest.getParagraphId(), account.getHref());

            if (userNotes != null && !userNotes.isEmpty()) {
                if (userNotes.get(0) != null) {
                    userNoteService.delete(userNotes.get(0));
                }
            }
            response.setError(false);
        } catch (Exception e) {
            response.setError(true);
        }

        return response;
    }

    @RequestMapping(path = "/completeParagraph", method = RequestMethod.POST)
    @ResponseBody
    public CompleteParagraphResponse completeParagraph(@RequestBody CompleteParagraphRequestParameters requestParameters,
                                                       HttpServletRequest request) {
        UserActivity userActivity = new UserActivity();
        Account account = accountResolver.getAccount(request);
        userActivity.setUserId(account.getHref());
        userActivity.setChapterId(requestParameters.getChapterId());
        userActivity.setCourseId(requestParameters.getCourseId());

        if (!StringUtils.isEmpty(requestParameters.getChapterId())) {
            userActivity.setParagraphId(requestParameters.getParagraphId());
        }

        // if the chapter will be also completed by this will be handled by service

        userActivityService.save(userActivity);

        List<UserActivity> userActivities = userActivityService.findAll();

        return new CompleteParagraphResponse(true, null);
    }
}
