package com.beat.jetlogger;

import com.beat.jetlogger.model.JetUser;
import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JetloggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JetloggerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JetUserRepository jetUserRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            //jetUserRepository.save(new JetUser("jet", passwordEncoder.encode("radio"), "Jet", "Jet"));
        };
    }

}
