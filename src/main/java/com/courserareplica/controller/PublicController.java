package com.courserareplica.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PublicController {

    @RequestMapping("/")
    public String landOnHomePage() {
        return "public";
    }

    @RequestMapping("/about")
    public String landOnHomePage1() {
        return "index";
    }
}
