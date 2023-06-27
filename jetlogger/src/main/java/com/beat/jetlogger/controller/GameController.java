package com.beat.jetlogger.controller;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.GameLog;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameLogRepository;
import com.beat.jetlogger.repository.GameRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class GameController {
    private final GameListRepository gameListRepository;
    private final GameRepository gameRepository;
    private final GameLogRepository gameLogRepository;

    /**
     * Construtor do controlador responsável por gerenciar as rotas relacionadas aos jogos.
     * @param gameListRepository Spring Data JPA Repository para a entidade GameList.
     * @param gameRepository Spring Data JPA Repository para a entidade Game.
     * @param gameLogRepository Spring Data JPA Repository para a entidade GameLog.
     */
    GameController(GameListRepository gameListRepository, GameRepository gameRepository, GameLogRepository gameLogRepository) {
        this.gameListRepository = gameListRepository;
        this.gameRepository = gameRepository;
        this.gameLogRepository = gameLogRepository;
    }

    /**
     * Rota GET para a página de criação de uma nova lista de jogos.
     * @param listId ID da lista na qual o jogo será inserido.
     * @param model Modelo para a página.
     * @return Retorna a página para criação de um novo jogo.
     */
    @GetMapping("/list/{id}/create-game")
    public String getCreateGame(@PathVariable("id") String listId, Model model) {
        GameList gameList = gameListRepository.findById(UUID.fromString(listId)).orElseThrow();
        String listName = gameList.getName();
        model.addAttribute("listName", listName);
        model.addAttribute("format", "Adicionar");
        model.addAttribute("listId", listId);
        return "create-game";
    }

    /**
     * Rota POST para a criação de um novo jogo.
     * @param name Nome do jogo.
     * @param platform Plataforma na qual o jogo foi jogado.
     * @param listId ID da lista na qual o jogo será inserido.
     * @return Retorna a página da lista de jogos na qual o jogo foi inserido.
     */
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

    /**
     * Rota GET para a página de edição de um jogo.
     * @param gameId ID do jogo a ser editado.
     * @param model Modelo para a página.
     * @return Retorna a página para edição de um jogo.
     */
    @GetMapping("/game/{id}/edit")
    public String getEditGame(@PathVariable("id") String gameId, Model model) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        String listName = game.getList().getName();
        model.addAttribute("listName", listName);
        model.addAttribute("listId", game.getList().getId());
        model.addAttribute("format", "Editar");
        model.addAttribute("game", game);
        return "create-game";
    }

    /**
     * Rota POST para a edição de um jogo.
     * @param name Nome do jogo.
     * @param platform Plataforma na qual o jogo foi jogado.
     * @param gameId ID do jogo a ser editado.
     * @return Retorna um redirect à página da lista de jogos a qual o jogo pertence.
     */
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

    /**
     * Rota GET para a página de confirmação de exclusão de um jogo.
     * DELETE não é suportado pelo HTML, então a exclusão é feita através de um GET.
     * @param gameId ID do jogo a ser excluído.
     * @return Retorna um redirect à página da lista a qual o jogo pertencia.
     */
    @GetMapping("/game/{id}/delete")
    public String postDeleteGame(@PathVariable("id") String gameId) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        UUID listId = game.getList().getId();
        gameRepository.delete(game);
        return "redirect:/list/" + listId;
    }

    /**
     * Rota GET para zerar um jogo.
     * @param gameId ID do jogo a ser zerado.
     * @param model Modelo para a página.
     * @return Retorna a página para zerar um jogo.
     */
    @GetMapping("/game/{id}/beat")
    public String getBeatGame(@PathVariable("id") String gameId, Model model) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        model.addAttribute("game", game);
        model.addAttribute("listId", game.getList().getId());
        return "beat-game";
    }

    /**
     * Rota POST para zerar um jogo.
     * @param model Modelo para a página.
     * @param gameId ID do jogo a ser zerado.
     * @param startedDate Data de início do zeramento.
     * @param finishedDate Data de término do zeramento.
     * @param hoursPlayed Tempo de jogo: horas.
     * @param minutesPlayed Tempo de jogo: minutos.
     * @param secondsPlayed Tempo de jogo: segundos.
     * @param rating Nota dada ao jogo pelo usuário.
     * @param mastered Booleano que indica se o jogo foi "detonado" ou não.
     * @return Retorna um redirect à página da lista de jogos a qual o jogo pertence.
     */
    @PostMapping("/game/{id}/beat")
    public String postBeatGame(
            Model model,
            @PathVariable("id") String gameId,
            @RequestParam("started_date") LocalDate startedDate,
            @RequestParam("finished_date") LocalDate finishedDate,
            @RequestParam("hours") int hoursPlayed,
            @RequestParam("minutes") int minutesPlayed,
            @RequestParam("seconds") int secondsPlayed,
            @RequestParam("rating") int rating,
            @RequestParam(value = "mastered", required = false) String mastered
    ) {
        Game game = gameRepository.findById(UUID.fromString(gameId)).orElseThrow();
        GameList list = game.getList();

        int hoursInSeconds = hoursPlayed * 3600;
        int minutesInSeconds = minutesPlayed * 60;
        Integer totalSecondsPlayed = hoursInSeconds + minutesInSeconds + secondsPlayed;

        Boolean masteredBoolean = false;
        if(mastered != null) masteredBoolean = true;

        GameLog gameLog = new GameLog(
                game,
                list,
                rating,
                startedDate,
                finishedDate,
                totalSecondsPlayed,
                masteredBoolean
        );

        gameLogRepository.save(gameLog);

        return "redirect:/list/" + list.getId();
    }
}
