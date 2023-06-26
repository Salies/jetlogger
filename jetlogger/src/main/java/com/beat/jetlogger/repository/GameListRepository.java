package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

import com.beat.jetlogger.projections.GameListNameAndCreated;

public interface GameListRepository extends CrudRepository<GameList, UUID> {
    // Engraçado ele dar um warning não um erro quando a interface não é compliant com o objeto "pai"
    List<GameListNameAndCreated> findAllByJetUserCreated(JetUser user);
}