package com.github.petrovyegor.tennisscoreboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "\"Players\"")
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"Id\"")
    @Positive(message = "ID should be positive number")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "\"Name\"", unique = true, nullable = false)
    @Size(min = 3, max = 15, message = "Name should be from {min} to {max} symbols")
    @NotBlank(message = "Name can not be null or contain only white spaces")
    @Pattern(regexp = "[a-zA-Z]+", message = "The name should contain Latin letters")
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
