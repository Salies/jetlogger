package com.beat.jetlogger.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_list")
public class GameList {
    @Id
    @Column(name = "list_id", nullable = false)
    @Getter private UUID id;

    @Column(name = "list_name", nullable = false)
    @Getter @Setter private String listName;

    @ManyToOne
    @JoinColumn(name = "user_created", nullable = false)
    @Getter @Setter private JetUser jetUserCreated;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Getter @Setter private LocalDate createdAt;

    public GameList() {}

    public GameList(String listName, JetUser jetUserCreated) {
        this.id = UUID.randomUUID();
        this.listName = listName;
        this.jetUserCreated = jetUserCreated;
    }
}