package com.beat.jetlogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListsController {
    @GetMapping("/lists")
    String lists() {
        return "lists";
    }
}
