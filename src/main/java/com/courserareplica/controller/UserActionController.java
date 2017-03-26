package com.courserareplica.controller;


import com.courserareplica.DTO.CompleteParagraphRequestParameters;
import com.courserareplica.DTO.CompleteParagraphResponse;
import com.courserareplica.model.UserActivity;
import com.courserareplica.service.UserActivityService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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

    @Autowired
    private AccountResolver accountResolver;

    @RequestMapping(path = "/completeParagraph", method = RequestMethod.POST)
    @ResponseBody
    public CompleteParagraphResponse completeParagraph(@RequestBody CompleteParagraphRequestParameters requestParameters,
                                                       HttpServletRequest request) {
        UserActivity userActivity = new UserActivity();
        Account account = accountResolver.getAccount(request);
        userActivity.setUserId(account.getHref());
        userActivity.setChapterId(requestParameters.getChapterId());

        if (!StringUtils.isEmpty(requestParameters.getChapterId())) {
            userActivity.setParagraphId(requestParameters.getParagraphId());
        }

        // if the chapter will be also completed by this will be handled by service

        userActivityService.save(userActivity);

        List<UserActivity> userActivities = userActivityService.findAll();

        return new CompleteParagraphResponse(true, null);
    }
}
