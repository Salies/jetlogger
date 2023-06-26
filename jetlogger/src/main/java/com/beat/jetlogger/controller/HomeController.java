package com.beat.jetlogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping("/")
    public String getHome(Principal principal) {
        // redirect to /lists se estiver logado
        if (principal != null) return "redirect:/lists";
        // este último return não será usado (Spring Security redireciona automaticamente)
        // mas é melhor do que fazer a função retornar null
        return "login";
    }
}
