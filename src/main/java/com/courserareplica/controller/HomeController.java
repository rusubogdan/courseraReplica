package com.courserareplica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {

	@RequestMapping
	public String home(Model m) {
		m.addAttribute("activeNavButton", "home");
		return "index";
	}
}
