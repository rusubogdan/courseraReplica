package com.courserareplica.controller;

import com.courserareplica.model.Course;
import com.courserareplica.service.UserActivityService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserActivityService userActivityService;

    /**
     * This will be accessed after a successful login by default
     * Only authorized users should end up here
     */
	@RequestMapping
	public String home(Model model, ServletRequest request) {
		model.addAttribute("activeNavButton", "home");

        Account account = AccountResolver.INSTANCE.getAccount(request);

        List<Course> userCourses = userActivityService.getUserCourses(account);
        model.addAttribute("userCourses", userCourses);

		return "home";
	}
}
