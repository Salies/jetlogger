package com.beat.jetlogger.controller;

import com.beat.jetlogger.repository.GameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameListController {
    @Autowired
    private final GameListRepository gameListRepository;

    public GameListController(GameListRepository gameListRepository) {
        this.gameListRepository = gameListRepository;
    }

    @GetMapping("/game-list/{id}")
    public String getGameList() {
        return "game-list";
    }
}
