package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.model.SecurityUser;
import com.beat.jetlogger.projections.Platform;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameLogRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class ListsController {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;
    private final GameLogRepository gameLogRepository;

    ListsController(GameListRepository gameListRepository, GameRepository gameRepository, GameLogRepository gameLogRepository) {
        this.gameListRepository = gameListRepository;
        this.gameRepository = gameRepository;
        this.gameLogRepository = gameLogRepository;
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

        // listas do usuário
        Iterable<GameList> lists;
        // count de games adicionados em listas
        Integer countGames;
        // plataformas pra alimentar o dropdown
        // daria pra pegar o count por aqui também, mas aí tem cara de gambiarra
        Iterable<Platform> platforms;
        // segundos jogados pelo usuário
        Integer secondsPlayed;
        // jogos zerados pelo usuário
        Integer countPlayed;

        // displayName do usuário
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();

        lists = this.gameListRepository.findGameListsByJetUserCreated_Id(user.getId());

        if(!dateRange.equals("all")) {
            LocalDateTime dateTime = switch (dateRange) {
                case "week" -> LocalDateTime.now().minusWeeks(1);
                case "month" -> LocalDateTime.now().minusMonths(1);
                case "year" -> LocalDateTime.now().minusYears(1);
                default -> LocalDateTime.now();
            };

            LocalDate date = dateTime.toLocalDate();

            if(platform.equals("all")){
                countGames = this.gameRepository.countByListJetUserCreatedAndCreatedAtAfter(user, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUserAndAfterDate(user, date);
                countPlayed = this.gameLogRepository.countByListJetUserCreatedAndCreatedAtAfter(user, date);
            }
            else {
                countGames = this.gameRepository.countByListJetUserCreatedAndPlatformAndCreatedAtAfter(user, platform, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUserAndPlatformAndAfterDate(user, platform, date);
                countPlayed = this.gameLogRepository.countByListJetUserCreatedAndGamePlatformAndCreatedAtAfter(user, platform, date);
            }
        } else if (!platform.equals("all")) {
            countGames = this.gameRepository.countByListJetUserCreatedAndPlatform(user, platform);
            secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUserAndPlatform(user, platform);
            countPlayed = this.gameLogRepository.countByListJetUserCreatedAndGamePlatform(user, platform);
        } else {
            countGames = this.gameRepository.countByListJetUserCreated(user);
            secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUser(user);
            countPlayed = this.gameLogRepository.countByListJetUserCreated(user);
        }

        // a p*** da soma retorna nulo
        if(secondsPlayed == null) secondsPlayed = 0;

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
        model.addAttribute("secondsPlayed", secondsPlayed);
        model.addAttribute("countPlayed", countPlayed);

        return "lists";
    }

    @GetMapping("/list/create")
    String createList() {
        return "create-list";
    }

    @PostMapping("/list/create")
    String postCreateList(@RequestParam String name, Authentication authentication){
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();
        GameList list = new GameList(name, user);
        this.gameListRepository.save(list);

        return "redirect:/lists";
    }

    @GetMapping("/list/{id}")
    String list(
            Authentication authentication,
            Model model,
            @PathVariable("id") String id,
            @RequestParam(value = "platform", required = false) String platform,
            @RequestParam(value = "date_range", required = false) String dateRange
    ) {
        if(platform == null) platform="all";
        if(dateRange == null) dateRange="all";

        if(platform.isEmpty()) platform="all";
        if(dateRange.isEmpty()) dateRange="all";

        // displayName do usuário
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();

        // get list by id
        GameList list = this.gameListRepository.findById(UUID.fromString(id)).orElseThrow();

        // get games by list
        Iterable<Game> games = this.gameRepository.findAllByList(list);

        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("games", games);
        model.addAttribute("countGames", 0);
        model.addAttribute("platforms", 0);
        model.addAttribute("secondsPlayed", 0);
        model.addAttribute("countPlayed", 0);

        return "list";
    }
}
