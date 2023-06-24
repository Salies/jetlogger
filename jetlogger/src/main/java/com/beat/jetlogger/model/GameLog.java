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
@Table(name = "game_log")
public class GameLog {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @Getter private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @Getter private GameList list;

    @Column(name = "rating", nullable = false)
    @Getter @Setter private Integer rating;

    @Column(name = "started_date", nullable = false)
    @Getter @Setter private LocalDate startedDate;

    @Column(name = "finished_date", nullable = false)
    @Getter @Setter private LocalDate finishedDate;

    @Column(name = "seconds_played", nullable = false)
    @Getter @Setter private Integer secondsPlayed;

    @Column(name = "mastered", nullable = false)
    @Getter @Setter private Boolean mastered;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @Getter @Setter private LocalDate createdAt;
}