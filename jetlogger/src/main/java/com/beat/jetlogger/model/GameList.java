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

    public GameList() {}

    public GameList(String name, JetUser jetUserCreated) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.jetUserCreated = jetUserCreated;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Basic(optional = false)
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)
    private JetUser jetUserCreated;
}