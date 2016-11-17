package com.courserareplica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    /**
     * This will be accessed after a successful login by default
     * Only authorized users should end up here
     */
	@RequestMapping
	public String home() {
		return "home";
	}
}
