package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.model.SecurityUser;
import com.beat.jetlogger.projections.GameListNameAndCreated;
import com.beat.jetlogger.repository.GameListRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListsController {
    private final GameListRepository gameListRepository;

    ListsController(GameListRepository gameListRepository) {
        this.gameListRepository = gameListRepository;
    }

    @GetMapping("/lists")
    String lists(Authentication authentication, Model model) {
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();
        model.addAttribute("displayName", user.getDisplayName());
        List<GameListNameAndCreated> lists = gameListRepository.findAllByJetUserCreated(user);
        model.addAttribute("gameLists", lists);
        return "lists";
    }
}
