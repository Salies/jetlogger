package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameListRepository extends JpaRepository<GameList, UUID> {
}