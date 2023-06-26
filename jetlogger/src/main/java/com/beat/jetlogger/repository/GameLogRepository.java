package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.GameLog;
import com.beat.jetlogger.model.JetUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface GameLogRepository extends CrudRepository<GameLog, UUID> {
    Integer countByListJetUserCreated(JetUser user);
    Integer countByListJetUserCreatedAndGamePlatform(JetUser user, String platform);
    Integer countByListJetUserCreatedAndCreatedAtAfter(JetUser user, LocalDate date);
    Integer countByListJetUserCreatedAndGamePlatformAndCreatedAtAfter(JetUser user, String platform, LocalDate date);

    // sum of seconds_played by JetUser
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1")
    Integer sumSecondsPlayedByListByJetUser(JetUser user);

    // sum of seconds_played by JetUser and platform
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2")
    Integer sumSecondsPlayedByListByJetUserAndPlatform(JetUser user, String platform);

    // sum of seconds_played by JetUser and after date
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.createdAt > ?2")
    Integer sumSecondsPlayedByListByJetUserAndAfterDate(JetUser user, LocalDate date);

    // sum of seconds_played by JetUser and platform and after date
    @Query("SELECT SUM(g.secondsPlayed) FROM GameLog g WHERE g.list.jetUserCreated = ?1 AND g.game.platform = ?2 AND g.createdAt > ?3")
    Integer sumSecondsPlayedByListByJetUserAndPlatformAndAfterDate(JetUser user, String platform, LocalDate date);
}
