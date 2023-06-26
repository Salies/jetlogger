package com.beat.jetlogger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jet_user")
public class JetUser implements UserDetails {
    public JetUser() {}

    public JetUser(String username, String password, String displayName) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Basic(optional = false)
    @Column(name = "display_name", nullable = false, length = 32)
    private String displayName;

    @OneToMany(mappedBy = "jetUserCreated")
    private Collection<GameList> gameLists;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}