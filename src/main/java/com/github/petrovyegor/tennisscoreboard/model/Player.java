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
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "\"Name\"", unique = true, nullable = false)
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public Player(int id, String name) {//нужен был для теста
        this.id = id;
        this.name = name;
    }
}
