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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @Getter private Game game;

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

    public GameLog() {
    }

    public GameLog(Game game, GameList list, Integer rating, LocalDate startedDate, LocalDate finishedDate, Integer secondsPlayed, Boolean mastered) {
        this.game = game;
        this.list = list;
        this.rating = rating;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.secondsPlayed = secondsPlayed;
        this.mastered = mastered;
    }
}