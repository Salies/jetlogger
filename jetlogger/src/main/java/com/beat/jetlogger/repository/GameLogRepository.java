package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.GameLog;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.projections.FullGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GameLogRepository extends CrudRepository<GameLog, UUID> {
    /**
     * Contagem de jogos adicionados por um usuário.
     * @param user Usuário.
     * @return Quantidade de jogos adicionados pelo usuário.
     */
    Integer countByListJetUserCreated(JetUser user);

    /**
     * Contagem de jogos adicionados por um usuário, filtrando por plataforma.
     * @param user Usuário.
     * @param platform Plataforma.
     * @return Quantidade de jogos adicionados pelo usuário na plataforma.
     */
    Integer countByListJetUserCreatedAndGamePlatform(JetUser user, String platform);

    /**
     * Contagem de jogos adicionados por um usuário, filtrando por data máxima de adição.
     * @param user Usuário.
     * @param date Data máxima de adição dos jogos.
     * @return Quantidade de jogos adicionados pelo usuário após a data.
     */
    Integer countByListJetUserCreatedAndFinishedDateAfter(JetUser user, LocalDate date);

    /**
     * Contagem de jogos adicionados por um usuário, filtrando por plataforma e data máxima de adição.
     * @param user Usuário.
     * @param platform Plataforma.
     * @param date Data máxima de adição dos jogos.
     * @return Quantidade de jogos adicionados pelo usuário na plataforma após a data.
     */
    Integer countByListJetUserCreatedAndGamePlatformAndFinishedDateAfter(JetUser user, String platform, LocalDate date);
    // mesma coisa, só que pra lista

    /**
     * Contagem de jogos adicionados em uma lista.
     * @param list Lista.
     * @return Quantidade de jogos adicionados na lista.
     */
    Integer countByList(GameList list);

    /**
     * Contagem de jogos adicionados em uma lista, filtrando por plataforma.
     * @param list Lista.
     * @param platform Plataforma.
     * @return Quantidade de jogos adicionados na lista cuja plataforma é a especificada.
     */
    Integer countByListAndGamePlatform(GameList list, String platform);

    /**
     * Contagem de jogos zerados em uma lista, filtrando por data máxima de adição.
     * @param list Lista.
     * @param date Data máxima de adição dos jogos.
     * @return Quantidade de jogos zerados na lista após a data.
     */
    Integer countByListAndFinishedDateAfter(GameList list, LocalDate date);

    /**
     * Contagem de jogos zerados em uma lista, filtrando por plataforma e data máxima de zeramento.
     * @param list Lista.
     * @param platform Plataforma.
     * @param date Data máxima de adição dos jogos.
     * @return Quantidade de jogos zerados na lista cuja plataforma é a especificada após a data.
     */
    Integer countByListAndGamePlatformAndFinishedDateAfter(GameList list, String platform, LocalDate date);

    // fill all GameLog by GameList, join with Game

    /**
     * Retorna todos os jogos adicionados em uma lista, com informações do zeramento.
     * @param list Lista.
     * @return Lista de jogos zerados na lista.
     */
    @Query("SELECT g.game.gameName AS gameName, g.game.platform AS platform, g.game.createdAt AS createdAt, g.game.id as gameId, " +
            "g.secondsPlayed AS secondsPlayed, g.rating AS rating, g.startedDate AS startedDate, g.finishedDate AS finishedDate, g.mastered AS mastered " +
            "FROM GameLog g WHERE g.list = ?1")
    List<FullGame> findAllByList(GameList list);

    // sum of seconds_played by

    /**
     * Soma de segundos jogados por um usuário.
     * @param user Usuário.
     * @return Soma de segundos jogados pelo usuário.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1")
    Integer sumSecondsPlayedByListByJetUser(JetUser user);

    // sum of seconds_played by JetUser and platform

    /**
     * Soma de segundos jogados por um usuário, filtrando por plataforma.
     * @param user Usuário.
     * @param platform Plataforma.
     * @return Soma de segundos jogados pelo usuário na plataforma especificada.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2")
    Integer sumSecondsPlayedByListByJetUserAndPlatform(JetUser user, String platform);

    // sum of seconds_played by JetUser and after date

    /**
     * Soma de segundos jogados por um usuário, filtrando por data máxima de adição.
     * @param user Usuário.
     * @param date Data máxima de adição dos jogos.
     * @return Soma de segundos jogados pelo usuário após a data.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.finishedDate > ?2")
    Integer sumSecondsPlayedByListByJetUserAndAfterDate(JetUser user, LocalDate date);

    // sum of seconds_played by JetUser and platform and after date

    /**
     * Soma de segundos jogados por um usuário, filtrando por plataforma e data máxima de adição.
     * @param user Usuário.
     * @param platform Plataforma.
     * @param date Data máxima de adição dos jogos.
     * @return Soma de segundos jogados pelo usuário na plataforma especificada após a data.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2 AND g.finishedDate > ?3")
    Integer sumSecondsPlayedByListByJetUserAndPlatformAndAfterDate(JetUser user, String platform, LocalDate date);

    // mesma coisa, só que pra lista

    /**
     * Soma de segundos jogados em uma lista.
     * @param list Lista.
     * @return Soma de segundos jogados na lista.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1")
    Integer sumSecondsPlayedByList(GameList list);

    /**
     * Soma de segundos jogados em uma lista, filtrando por plataforma.
     * @param list Lista.
     * @param platform Plataforma.
     * @return Soma de segundos jogados na lista cuja plataforma é a especificada.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.game.platform = ?2")
    Integer sumSecondsPlayedByListAndPlatform(GameList list, String platform);

    /**
     * Soma de segundos jogados em uma lista, filtrando por data máxima de adição.
     * @param list Lista.
     * @param date Data máxima de adição dos jogos.
     * @return Soma de segundos jogados na lista após a data.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.finishedDate > ?2")
    Integer sumSecondsPlayedByListAndAfterDate(GameList list, LocalDate date);

    /**
     * Soma de segundos jogados em uma lista, filtrando por plataforma e data máxima de adição.
     * @param list Lista.
     * @param platform Plataforma.
     * @param date Data máxima de adição dos jogos.
     * @return Soma de segundos jogados na lista cuja plataforma é a especificada após a data.
     */
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.game.platform = ?2 AND g.finishedDate > ?3")
    Integer sumSecondsPlayedByListAndPlatformAndAfterDate(GameList list, String platform, LocalDate date);
}
