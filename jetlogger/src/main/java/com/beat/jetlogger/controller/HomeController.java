package com.beat.jetlogger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {
    @GetMapping("/")
    public String getHome(Principal principal) {
        return "Hello " + principal.getName();
    }
}
