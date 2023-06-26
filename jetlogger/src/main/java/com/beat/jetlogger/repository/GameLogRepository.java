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
    Integer countByListJetUserCreated(JetUser user);
    Integer countByListJetUserCreatedAndGamePlatform(JetUser user, String platform);
    Integer countByListJetUserCreatedAndFinishedDateAfter(JetUser user, LocalDate date);
    Integer countByListJetUserCreatedAndGamePlatformAndFinishedDateAfter(JetUser user, String platform, LocalDate date);
    // mesma coisa, só que pra lista
    Integer countByList(GameList list);
    Integer countByListAndGamePlatform(GameList list, String platform);
    Integer countByListAndFinishedDateAfter(GameList list, LocalDate date);
    Integer countByListAndGamePlatformAndFinishedDateAfter(GameList list, String platform, LocalDate date);

    // fill all GameLog by GameList, join with Game
    @Query("SELECT g.game.gameName AS gameName, g.game.platform AS platform, g.game.createdAt AS createdAt, g.game.id as gameId, " +
            "g.secondsPlayed AS secondsPlayed, g.rating AS rating, g.startedDate AS startedDate, g.finishedDate AS finishedDate, g.mastered AS mastered " +
            "FROM GameLog g WHERE g.list = ?1")
    List<FullGame> findAllByList(GameList list);

    // sum of seconds_played by JetUser
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1")
    Integer sumSecondsPlayedByListByJetUser(JetUser user);

    // sum of seconds_played by JetUser and platform
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2")
    Integer sumSecondsPlayedByListByJetUserAndPlatform(JetUser user, String platform);

    // sum of seconds_played by JetUser and after date
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.finishedDate > ?2")
    Integer sumSecondsPlayedByListByJetUserAndAfterDate(JetUser user, LocalDate date);

    // sum of seconds_played by JetUser and platform and after date
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2 AND g.finishedDate > ?3")
    Integer sumSecondsPlayedByListByJetUserAndPlatformAndAfterDate(JetUser user, String platform, LocalDate date);

    // mesma coisa, só que pra lista
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1")
    Integer sumSecondsPlayedByList(GameList list);

    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.game.platform = ?2")
    Integer sumSecondsPlayedByListAndPlatform(GameList list, String platform);

    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.finishedDate > ?2")
    Integer sumSecondsPlayedByListAndAfterDate(GameList list, LocalDate date);

    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list = ?1 AND g.game.platform = ?2 AND g.finishedDate > ?3")
    Integer sumSecondsPlayedByListAndPlatformAndAfterDate(GameList list, String platform, LocalDate date);
}
