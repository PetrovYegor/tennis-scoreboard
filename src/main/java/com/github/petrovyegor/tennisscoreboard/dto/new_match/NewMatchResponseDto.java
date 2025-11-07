package com.github.petrovyegor.tennisscoreboard.dto.new_match;

import java.util.UUID;

public record NewMatchResponseDto(UUID matchUuid) {//TODO вероятно подлежит рефакторингу, пока не понял, оставить рекордом или сделать класс с аннотациями ломбока
}
