package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.projections.Platform;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
    Integer countByListJetUserCreated(JetUser user);
    Integer countByListJetUserCreatedAndPlatform(JetUser user, String platform);
    Integer countByListJetUserCreatedAndCreatedAtAfter(JetUser user, LocalDateTime date);
    Integer countByListJetUserCreatedAndPlatformAndCreatedAtAfter(JetUser user, String platform, LocalDateTime date);
    Iterable<Platform> findAllByListJetUserCreated(JetUser user);
    Iterable<Game> findAllByList(GameList list);
}
