package com.beat.jetlogger.controller;

import com.beat.jetlogger.config.SecurityConfig;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.repository.JetUserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final JetUserRepository jetUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    LoginController(JetUserRepository jetUserRepository, PasswordEncoder passwordEncoder) {
        this.jetUserRepository = jetUserRepository;
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/register")
    String register() {
        return "register";
    }

    @PostMapping("/register")
    String registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("display-name") String displayName
    ) {
        JetUser newUser = new JetUser(username, passwordEncoder.encode(password), displayName);

        // Criando novo usuário
        try {
            jetUserRepository.save(newUser);
        } catch (Exception e) {
            // Caso não seja possível, informar ao usuário
            return "redirect:/register?error";
        }

        // Ia tratar erro aqui mas pelo visto não é trivial, então dane-se.

        return "registration-success";
    }
}
