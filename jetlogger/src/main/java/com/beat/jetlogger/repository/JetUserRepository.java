package com.beat.jetlogger.repository;

import com.beat.jetlogger.model.JetUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface JetUserRepository extends CrudRepository<JetUser, UUID> {
    Optional<JetUser> findByUsername(String username);
}