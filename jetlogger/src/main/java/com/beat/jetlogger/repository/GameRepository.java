package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.projections.Platform;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface GameRepository extends CrudRepository<Game, UUID> {
    /**
     * Conta quantos jogos foram criados por um usuário.
     * @param user Usuário
     * @return Quantidade de jogos criados pelo usuário.
     */
    Integer countByListJetUserCreated(JetUser user);

    /**
     * Conta quantos jogos foram criados por um usuário cuja plataforma é a especificada.
     * @param user Usuário
     * @param platform Plataforma
     * @return Quantidade de jogos criados pelo usuário na plataforma especificada.
     */
    Integer countByListJetUserCreatedAndPlatform(JetUser user, String platform);

    /**
     * Conta quantos jogos foram criados por um usuário após uma data.
     * @param user Usuário
     * @param date Data
     * @return Quantidade de jogos criados pelo usuário após a data especificada.
     */
    Integer countByListJetUserCreatedAndCreatedAtAfter(JetUser user, LocalDateTime date);

    /**
     * Conta quantos jogos foram criados por um usuário cuja plataforma é, adicionado após uma data.
     * @param user Usuário
     * @param platform Plataforma
     * @param date Data
     * @return Quantidade de jogos criados pelo usuário cuja plataforma é a especificada após a data especificada.
     */
    Integer countByListJetUserCreatedAndPlatformAndCreatedAtAfter(JetUser user, String platform, LocalDateTime date);

    /**
     * Retorna todas as plataformas para quais o usuário adicionou jogos.
     * @param user Usuário
     * @return Plataformas.
     */
    Iterable<Platform> findAllByListJetUserCreated(JetUser user);

    // mesma coisa, mas para lista

    /**
     * Retorna todos os jogos adicionados a uma lista.
     * @param list Lista
     * @return Jogos adicionados a lista.
     */
    Iterable<Game> findAllByList(GameList list);

    /**
     * Retorna todos os jogos adicionados a uma lista cuja plataforma é a especificada.
     * @param list Lista
     * @return Jogos adicionados a lista cuja plataforma é a especificada.
     */
    Integer countByList(GameList list);

    /**
     * Retorna todos os jogos adicionados a uma lista após uma data.
     * @param list Lista
     * @param platform Plataforma
     * @return Jogos adicionados a lista após a data especificada.
     */
    Integer countByListAndPlatform(GameList list, String platform);

    /**
     * Retorna todos os jogos adicionados a uma lista cuja plataforma é a especificada após uma data.
     * @param list Lista
     * @param date Data
     * @return Jogos adicionados a lista cuja plataforma é a especificada após a data especificada.
     */
    Integer countByListAndCreatedAtAfter(GameList list, LocalDateTime date);

    /**
     * Retorna todos os jogos adicionados a uma lista cuja plataforma é a especificada, adicionado após uma data.
     * @param list Lista
     * @param platform Plataforma
     * @param date Data de adição
     * @return Jogos adicionados a lista cuja plataforma é a especificada após a data especificada.
     */
    Integer countByListAndPlatformAndCreatedAtAfter(GameList list, String platform, LocalDateTime date);
}
