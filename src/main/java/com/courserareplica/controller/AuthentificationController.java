package com.courserareplica.controller;


import com.courserareplica.service.AuthService;
import com.stormpath.sdk.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthentificationController {

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.GET, path = "/signup")
    public String createUser(@RequestParam String email,
                             @RequestParam String password) {

        Account account = authService.createUserAccount(email, email, password);

        return "home";
    }

    @RequestMapping(method = RequestMethod.GET, path = "login")
    public String logInUser(@RequestParam String email,
                            @RequestParam String password) {
        Account account = authService.getUserAccount(email, password);

        return "home";
    }
}
