package com.beat.jetlogger;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameRepository;
import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class JetloggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetloggerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JetUserRepository jetUserRepository, PasswordEncoder passwordEncoder,
                                        GameListRepository gameListRepository,
                                        GameRepository gameRepository) {
        return args -> {
                // get list by id ef50e48e-9a37-4722-881b-e63e6f5ce28c
                /*Optional<GameList> gameList = gameListRepository.findById(UUID.fromString("ef50e48e-9a37-4722-881b-e63e6f5ce28c"));
                if(gameList.isPresent()) {
                    GameList list = gameList.get();
                    Game game = new Game(list, "a", "a");
                    gameRepository.save(game);
                }*/
        };
    }

}
