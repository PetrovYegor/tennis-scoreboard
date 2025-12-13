package com.github.petrovyegor.tennisscoreboard.dto.new_match;

public record NewMatchRequestDto(//название неподходящее, переименовать
                                 String firstPlayerName,
                                 String secondPlayerName
) {}
