package com.beat.jetlogger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jet_user")
public class JetUser {
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

    @Basic(optional = false)
    @Column(name = "real_name", nullable = false, length = 128)
    private String realName;
}