package com.beat.jetlogger;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.GameLog;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameLogRepository;
import com.beat.jetlogger.repository.GameRepository;
import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                                        GameRepository gameRepository,
                                        GameLogRepository gameLogRepository) {
        return args -> {
                // user d79774fa-71dd-4879-8da8-ef17eb373e67
                // list ef50e48e-9a37-4722-881b-e63e6f5ce28c
                // game 3404c91e-85fb-4a9b-acc1-6681624763bc
            /*Optional<JetUser> user = jetUserRepository.findById(UUID.fromString("d79774fa-71dd-4879-8da8-ef17eb373e67"));
            Optional<GameList> list = gameListRepository.findById(UUID.fromString("ef50e48e-9a37-4722-881b-e63e6f5ce28c"));
            Optional<Game> game = gameRepository.findById(UUID.fromString("3404c91e-85fb-4a9b-acc1-6681624763bc"));
            if(user.isPresent() && list.isPresent() && game.isPresent()) {
                GameLog gameLog = new GameLog(
                        game.get(),
                        list.get(),
                        5,
                        LocalDate.now(),
                        LocalDate.now(),
                        3600,
                        true
                );
                gameLogRepository.save(gameLog);
            }*/
        };
    }

}
