package com.beat.jetlogger.projections;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FullGame {
    String getGameName();
    String getPlatform();
    Integer getSecondsPlayed();
    Integer getRating();
    LocalDate getStartedDate();
    LocalDate getFinishedDate();
    Boolean  getMastered();
    String getGameId();
    LocalDateTime getCreatedAt();
}
