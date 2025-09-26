package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchScoreRequestDto {
    //private OngoingMatchDto ongoingMatchDto;
    private UUID matchUuid;
    private int roundWinnerId;

//    public MatchScoreRequestDto(OngoingMatchDto ongoingMatchDto, int roundWinnerId) {
//        this.ongoingMatchDto = ongoingMatchDto;
//        this.roundWinnerId = roundWinnerId;
//    }

    public MatchScoreRequestDto(UUID matchUuid, int roundWinnerId) {
        this.matchUuid = matchUuid;
        this.roundWinnerId = roundWinnerId;
    }
}
