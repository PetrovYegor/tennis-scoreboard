package com.github.petrovyegor.tennisscoreboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewMatchRequestDto {
    @Size(min = 3, max = 15, message = "Name should be from {min} to {max} symbols")
    @NotBlank(message = "Name can not be null or contain only white spaces")
    @Pattern(regexp = "[a-zA-Z]+", message = "The name should contain Latin letters")
    private String firstPlayerName;
    @Size(min = 3, max = 15, message = "Name should be from {min} to {max} symbols")
    @NotBlank(message = "Name can not be null or contain only white spaces")
    @Pattern(regexp = "[a-zA-Z]+", message = "The name should contain Latin letters")
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
