package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class GameController {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;

    GameController(GameListRepository gameListRepository, GameRepository gameRepository) {
        this.gameListRepository = gameListRepository;
        this.gameRepository = gameRepository;
    }

    /*@GetMapping("/game/{id}")
    public String getGame(@PathVariable("id") String id) {
        return "game";
    }*/

    @GetMapping("/list/{id}/game/create")
    public String getCreateGame(@PathVariable("id") String listId, Model model) {
        GameList gameList = gameListRepository.findById(UUID.fromString(listId)).orElseThrow();
        String listName = gameList.getName();
        model.addAttribute("listName", listName);
        model.addAttribute("format", "Adicionar");
        model.addAttribute("listId", listId);
        return "create-game";
    }
}
