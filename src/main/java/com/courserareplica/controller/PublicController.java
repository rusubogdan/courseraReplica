package com.courserareplica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 */
@Controller
@RequestMapping
public class PublicController {

    @RequestMapping("/public")
    public String publicPage() {
        return "index";
    }

    @RequestMapping("/")
    public String firstPage() {
        return "index";
    }
}
