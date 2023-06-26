package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/list/{id}/create-game")
    public String getCreateGame(@PathVariable("id") String listId, Model model) {
        GameList gameList = gameListRepository.findById(UUID.fromString(listId)).orElseThrow();
        String listName = gameList.getName();
        model.addAttribute("listName", listName);
        model.addAttribute("format", "Adicionar");
        model.addAttribute("listId", listId);
        return "create-game";
    }

    @PostMapping("/list/{id}/create-game")
    public String postCreateGame(
            @RequestParam("name") String name,
            @RequestParam("platform") String platform,
            @PathVariable("id") String listId
    ) {
        GameList list = gameListRepository.findById(UUID.fromString(listId)).orElseThrow();
        Game game = new Game(list, name, platform);
        gameRepository.save(game);
        return "redirect:/list/" + listId;
    }
}
