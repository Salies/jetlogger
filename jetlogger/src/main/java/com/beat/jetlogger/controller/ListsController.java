package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.model.SecurityUser;
import com.beat.jetlogger.projections.Platform;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
public class ListsController {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;

    ListsController(GameListRepository gameListRepository, GameRepository gameRepository) {
        this.gameListRepository = gameListRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/lists")
    String lists(
            Authentication authentication,
            Model model,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "date_range", required = false) String dateRange
    ) {
        if(platform == null) platform="all";
        if(dateRange == null) dateRange="all";

        if(platform.isEmpty()) platform="all";
        if(dateRange.isEmpty()) dateRange="all";

        // displayName do usuário
        JetUser user;
        // listas do usuário
        Iterable<GameList> lists;
        // count de games adicionados em listas
        Integer countGames;
        // plataformas pra alimentar o dropdown
        // daria pra pegar o count por aqui também, mas aí tem cara de gambiarra
        Iterable<Platform> platforms;

        user = ((SecurityUser) authentication.getPrincipal()).getUser();

        lists = this.gameListRepository.findGameListsByJetUserCreated_Id(user.getId());

        if(!dateRange.equals("all")) {
            LocalDateTime date = switch (dateRange) {
                case "week" -> LocalDateTime.now().minusWeeks(1);
                case "month" -> LocalDateTime.now().minusMonths(1);
                case "year" -> LocalDateTime.now().minusYears(1);
                default -> LocalDateTime.now();
            };

            if(platform.equals("all"))
                countGames = this.gameRepository.countByListJetUserCreatedAndCreatedAtAfter(user, date);
            else
                countGames = this.gameRepository.countByListJetUserCreatedAndPlatformAndCreatedAtAfter(user, platform, date);
        } else if (!platform.equals("all")) {
            countGames = this.gameRepository.countByListJetUserCreatedAndPlatform(user, platform);
        } else {
            countGames = this.gameRepository.countByListJetUserCreated(user);
        }

        platforms = this.gameRepository.findAllByListJetUserCreated(user);

        // to set of Strings (não repete plataformas)
        Set<String> platformsSet = new HashSet<>();
        for (Platform p : platforms) {
            platformsSet.add(p.getPlatform());
        }

        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("gameLists", lists);
        model.addAttribute("countGames", countGames);
        model.addAttribute("platforms", platformsSet);

        return "lists";
    }
}
