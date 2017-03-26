package com.courserareplica.controller;


import com.courserareplica.DTO.CompleteParagraphRequestParameters;
import com.courserareplica.DTO.CompleteParagraphResponse;
import com.courserareplica.model.UserActivity;
import com.courserareplica.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/v1/ajax")
public class UserActionController {

    @Autowired
    private UserActivityService userActivityService;

    @RequestMapping(path = "/completeParagraph", method = RequestMethod.POST)
    @ResponseBody
    public CompleteParagraphResponse completeParagraph(@RequestBody CompleteParagraphRequestParameters requestParameters,
                                                       HttpServletRequest request) {
        List<UserActivity> userActivities = userActivityService.findAll();

        return new CompleteParagraphResponse(true, null);
    }
}
