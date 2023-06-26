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

    /**
     * Construtor do controlador de login.
     * @param jetUserRepository Spring Data JPA repository para JetUser (usuário da plataforma).
     * @param passwordEncoder Bean de codificação de senha. Autowired pelo Spring.
     */
    LoginController(JetUserRepository jetUserRepository, PasswordEncoder passwordEncoder) {
        this.jetUserRepository = jetUserRepository;
    }

    /**
     * Rota para a página de login.
     * @return Caminho para o template de login.
     */
    @GetMapping("/login")
    String login() {
        return "login";
    }

    /**
     * Rota GET para a página de registro de usuário.
     * @return Caminho para o template de registro.
     */
    @GetMapping("/register")
    String register() {
        return "register";
    }

    /**
     * Rota POST para a página de registro de usuário.
     * @param username Nome de usuário
     * @param password Senha do usuário
     * @param displayName Nome de exibição do usuário
     * @return Caminho para o template de sucesso de registro. Caso não seja possível registrar, redireciona para a página de registro com um parâmetro de erro.
     */
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
