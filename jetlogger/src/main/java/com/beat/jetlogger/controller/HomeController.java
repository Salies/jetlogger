package com.beat.jetlogger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Rota root da aplicação.
     * @return Redireciona à rota /lists. Caso o usuário não esteja logado,
     * isso fará com que o Spring Security redirecione para a rota /login.
     */
    @GetMapping("/")
    public String getHome() {
        return "redirect:/lists";
    }
}
