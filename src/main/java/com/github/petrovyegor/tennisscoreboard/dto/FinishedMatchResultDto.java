package com.github.petrovyegor.tennisscoreboard.dto;

import java.util.UUID;

public class FinishedMatchResultDto {
    private UUID matchUuid;
    private int firstPlayerId;
    private int secondPlayerId;
    private int winnerId;
}
