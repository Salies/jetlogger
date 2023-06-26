package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/game/{id}/edit")
    public String getEditGame(@PathVariable("id") String gameId, Model model) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        String listName = game.getList().getName();
        model.addAttribute("listName", listName);
        model.addAttribute("format", "Editar");
        model.addAttribute("game", game);
        return "create-game";
    }

    @PostMapping("/game/{id}/edit")
    public String postEditGame(
            @RequestParam("name") String name,
            @RequestParam("platform") String platform,
            @PathVariable("id") String gameId
    ) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        game.setGameName(name);
        game.setPlatform(platform);
        gameRepository.save(game);
        return "redirect:/list/" + game.getList().getId();
    }

    @GetMapping("/game/{id}/delete")
    public String postDeleteGame(@PathVariable("id") String gameId) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        UUID listId = game.getList().getId();
        gameRepository.delete(game);
        return "redirect:/list/" + listId;
    }
}
