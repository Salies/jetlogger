package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.*;
import com.beat.jetlogger.projections.FullGame;
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
import java.util.*;

@Controller
public class ListsController {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;
    private final GameLogRepository gameLogRepository;

    /**
     * Construtor do controlador responsável pelas listas.
     * @param gameListRepository Spring Data JPA repository para a entidade GameList.
     * @param gameRepository Spring Data JPA repository para a entidade Game.
     * @param gameLogRepository Spring Data JPA repository para a entidade GameLog.
     */
    ListsController(GameListRepository gameListRepository, GameRepository gameRepository, GameLogRepository gameLogRepository) {
        this.gameListRepository = gameListRepository;
        this.gameRepository = gameRepository;
        this.gameLogRepository = gameLogRepository;
    }

    /**
     * Converte uma range de datas para um LocalDateTime.
     * @param dateRange String que representa o range de datas.
     * @return LocalDateTime que representa o range de datas.
     */
    private LocalDateTime fromDateRange(String dateRange) {
        return switch (dateRange) {
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            case "year" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.now();
        };
    }

    /**
     * Rota para a página de listas do usuário.
     * @param authentication Autenticação do usuário.
     * @param model Modelo da página.
     * @param platform Filtro para plataforma de jogos (se houver).
     * @param dateRange Filtro para range de datas (se houver).
     * @return Caminho para o template da página de listas.
     */
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
        // segundos jogados pelo usuário
        Integer secondsPlayed;
        // jogos zerados pelo usuário
        Integer countPlayed;

        // displayName do usuário
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();

        lists = this.gameListRepository.findGameListsByJetUserCreated_Id(user.getId());

        if(!dateRange.equals("all")) {
            LocalDateTime dateTime = fromDateRange(dateRange);
            LocalDate date = dateTime.toLocalDate();

            if(platform.equals("all")){
                countGames = this.gameRepository.countByListJetUserCreatedAndCreatedAtAfter(user, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUserAndAfterDate(user, date);
                countPlayed = this.gameLogRepository.countByListJetUserCreatedAndFinishedDateAfter(user, date);
            }
            else {
                countGames = this.gameRepository.countByListJetUserCreatedAndPlatformAndCreatedAtAfter(user, platform, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListByJetUserAndPlatformAndAfterDate(user, platform, date);
                countPlayed = this.gameLogRepository.countByListJetUserCreatedAndGamePlatformAndFinishedDateAfter(user, platform, date);
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

        // plataformas pra alimentar o dropdown
        // daria pra pegar o count por aqui também, mas aí tem cara de gambiarra
        Iterable<Platform> platforms = this.gameRepository.findAllByListJetUserCreated(user);

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

    /**
     * Rota GET para a página de criação de listas.
     * @return Caminho para o template da página de criação de listas.
     */
    @GetMapping("/list/create")
    String createList() {
        return "create-list";
    }

    /**
     * Rota POST para a criação de listas.
     * @param name Nome da lista.
     * @param authentication Autenticação do usuário.
     * @return Redirecionamento para a página de listas.
     */
    @PostMapping("/list/create")
    String postCreateList(@RequestParam String name, Authentication authentication){
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();
        GameList list = new GameList(name, user);
        this.gameListRepository.save(list);

        return "redirect:/lists";
    }

    /**
     * Rota GET para visualização de uma lista.
     * @param authentication Autenticação do usuário.
     * @param model Modelo da página.
     * @param id ID da lista.
     * @param platform Filtro para plataforma de jogos (se houver).
     * @param dateRange Filtro para range de datas (se houver).
     * @return Caminho para o template da página de visualização de uma lista.
     */
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

        // count de games adicionados na lista
        Integer countGames;
        // segundos jogados pelo usuário na lista
        Integer secondsPlayed;
        // jogos zerados pelo usuário na lista
        Integer countPlayed;

        // displayName do usuário
        JetUser user = ((SecurityUser) authentication.getPrincipal()).getUser();

        // get list by id
        GameList list = this.gameListRepository.findById(UUID.fromString(id)).orElseThrow();

        if(!dateRange.equals("all")) {
            LocalDateTime dateTime = fromDateRange(dateRange);
            LocalDate date = dateTime.toLocalDate();

            if(platform.equals("all")){
                countGames = this.gameRepository.countByListAndCreatedAtAfter(list, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListAndAfterDate(list, date);
                countPlayed = this.gameLogRepository.countByListAndFinishedDateAfter(list, date);
            }
            else {
                countGames = this.gameRepository.countByListAndPlatformAndCreatedAtAfter(list, platform, dateTime);
                secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListAndPlatformAndAfterDate(list, platform, date);
                countPlayed = this.gameLogRepository.countByListAndGamePlatformAndFinishedDateAfter(list, platform, date);
            }
        } else if (!platform.equals("all")) {
            countGames = this.gameRepository.countByListAndPlatform(list, platform);
            secondsPlayed = this.gameLogRepository.sumSecondsPlayedByListAndPlatform(list, platform);
            countPlayed = this.gameLogRepository.countByListAndGamePlatform(list, platform);
        } else {
            countGames = this.gameRepository.countByList(list);
            secondsPlayed = this.gameLogRepository.sumSecondsPlayedByList(list);
            countPlayed = this.gameLogRepository.countByList(list);
        }
        // a p*** da soma retorna nulo
        if(secondsPlayed == null) secondsPlayed = 0;

        // get games by list
        Iterable<Game> games = this.gameRepository.findAllByList(list);
        // criando os sets
        // plataforma
        Set<String> platformsSet = new HashSet<>();
        for(Game g : games) {
            platformsSet.add(g.getPlatform());
        }

        // lista de jogos zerados
        List<FullGame> played = this.gameLogRepository.findAllByList(list);
        List<Game> notPlayed = new ArrayList<>();
        String[] playedIds = new String[played.size()];
        for(int i = 0; i < played.size(); i++) {
            playedIds[i] = played.get(i).getGameId();
        }
        for(Game g : games) {
            if(!Arrays.asList(playedIds).contains(g.getId().toString())) {
                notPlayed.add(g);
            }
        }

        model.addAttribute("displayName", user.getDisplayName());
        model.addAttribute("countGames", countGames);
        model.addAttribute("platforms", platformsSet);
        model.addAttribute("secondsPlayed", secondsPlayed);
        model.addAttribute("countPlayed", countPlayed);
        model.addAttribute("listName", list.getName());
        model.addAttribute("playedGames", played);
        model.addAttribute("notPlayedGames", notPlayed);
        model.addAttribute("listId", id);

        return "list";
    }
}
