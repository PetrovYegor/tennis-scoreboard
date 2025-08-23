package com.github.petrovyegor.tennisscoreboard.dto;

import com.github.petrovyegor.tennisscoreboard.exception.InvalidParamException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class NewMatchRequestDto {
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private final String firstPlayerName;
    @NotNull(message = "Name can not be null")
    @Pattern(regexp = "[a-zA-Z]{3,15}", message = "The name should contain Latin letters, name length should be from 3 to 15 symbols")
    private final String secondPlayerName;

    public static void main(String[] args) {
        var temp = new NewMatchRequestDto(null,"bbb");

        System.out.println("object " + temp + " was created");
        vaidateRequestDto(temp);
        while (true){

        }
    }

    private static void vaidateRequestDto(NewMatchRequestDto newMatchRequestDto) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<NewMatchRequestDto>> violations = validator.validate(newMatchRequestDto);
        if (!violations.isEmpty()) {
            throw new InvalidParamException(violations.toString());
        }
    }
}
