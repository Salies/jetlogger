package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.GameList;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GameListRepository extends CrudRepository<GameList, UUID> {
    // Engraçado ele dar um warning não um erro quando a interface não é compliant com o objeto "pai"

    /**
     * Retorna todas as listas criadas por um usuário.
     * @param id ID do usuário.
     * @return Iterable de listas criadas pelo usuário.
     */
    Iterable<GameList> findGameListsByJetUserCreated_Id(UUID id);
}