package com.beat.jetlogger;

import com.beat.jetlogger.repository.GameListRepository;
import com.beat.jetlogger.repository.GameLogRepository;
import com.beat.jetlogger.repository.GameRepository;
import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JetloggerApplication {
    /**
     * Main method da aplicação.
     * @param args argumentos da linha de comando (nenhum esperado)
     */
    public static void main(String[] args) {
        SpringApplication.run(JetloggerApplication.class, args);
    }
}
