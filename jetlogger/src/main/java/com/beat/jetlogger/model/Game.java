package com.beat.jetlogger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "game_id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private GameList list;

    @Basic(optional = false)
    @Column(name = "game_name")
    private String gameName;

    // Opcional
    @Column(name = "cover_art_url", length = 2048)
    private String coverArtUrl;

    @Basic(optional = false)
    @Column(name = "platform")
    private String platform;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime addedAt;

    public Game() {}

    public Game(GameList list, String gameName, String platform) {
        this.list = list;
        this.gameName = gameName;
        this.platform = platform;
    }
}