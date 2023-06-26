package com.beat.jetlogger.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {
    @RequestMapping("/error")
    public String getError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = 666;
        String message = "Ocorreu um erro!";

        if(status != null) statusCode = Integer.parseInt(status.toString());
        if(statusCode == 404) message = "Página não encontrada.";
        if(statusCode == 500) message = "Erro interno do servidor.";
        if(statusCode == 403) message = "Acesso negado.";
        if(statusCode == 401) message = "Não autorizado.";
        if(statusCode == 400) message = "Requisição inválida.";

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("message", message);

        return "error";
    }
}
