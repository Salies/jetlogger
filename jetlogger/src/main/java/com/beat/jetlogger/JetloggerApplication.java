package com.beat.jetlogger;

import com.beat.jetlogger.model.Game;
import com.beat.jetlogger.model.GameList;
import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Optional;

@SpringBootApplication
public class JetloggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetloggerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JetUserRepository jetUserRepository, PasswordEncoder passwordEncoder,
                                        GameListRepository gameListRepository) {
        return args -> {
            try {
                jetUserRepository.save(new JetUser(
                        "gum",
                        passwordEncoder.encode("radio"),
                        "Gum"
                ));
            } catch (Exception e) {
                System.out.println("User already exists");
            }
            Optional<JetUser> jet = jetUserRepository.findByUsername("gum");
            if(jet.isPresent()) {
                JetUser jetUser = jet.get();
                gameListRepository.save(new GameList(
                        "lista teste",
                        jetUser
                ));
            }
        };
    }

}
