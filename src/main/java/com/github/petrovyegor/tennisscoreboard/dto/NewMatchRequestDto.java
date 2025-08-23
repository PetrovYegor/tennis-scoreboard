package com.github.petrovyegor.tennisscoreboard.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewMatchRequestDto {
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private String firstPlayerName;
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private String secondPlayerName;
}
