package com.beat.jetlogger.service;

import com.beat.jetlogger.model.SecurityUser;

import com.beat.jetlogger.repository.JetUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JetUserDetailsService implements UserDetailsService {
    private final JetUserRepository jetUserRepository;

    public JetUserDetailsService(JetUserRepository jetUserRepository) {
        this.jetUserRepository = jetUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jetUserRepository
                .findByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usário não encontrado: " + username));
    }
}
