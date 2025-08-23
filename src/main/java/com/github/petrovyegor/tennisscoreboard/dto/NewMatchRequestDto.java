package com.github.petrovyegor.tennisscoreboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewMatchRequestDto {
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private String firstPlayerName;
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private String secondPlayerName;

    public NewMatchRequestDto(String firstPlayerName, String secondPlayerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }
}
