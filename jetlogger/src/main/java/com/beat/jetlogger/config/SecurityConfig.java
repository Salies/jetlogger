package com.beat.jetlogger.config;

import com.beat.jetlogger.service.JetUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JetUserDetailsService userDetailsService;
    public SecurityConfig(JetUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configuração de segurança do Spring Security. Define o padrão de resposta para pedidos.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] allowed = new String[] {
                "/css/**",
                "/img/**",
                "/register"
        };
        // Permite que pedidos ao diretório /static (de recursos estáticos)
        // sejam respondidos sem necessidade de autenticação.
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(allowed).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/lists", true).permitAll())
                .logout((logout) -> logout.logoutSuccessUrl("/login"));

        return http.build();
    }

    /**
     * Configuração de encriptação de senhas.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}