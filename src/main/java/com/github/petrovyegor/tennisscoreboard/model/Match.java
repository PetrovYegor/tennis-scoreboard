package com.github.petrovyegor.tennisscoreboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "\"Matches\"")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"Id\"")
    @Positive(message = "ID should be positive number")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "\"Player1\"", nullable = false)
    @Positive(message = "firstPlayerId should be positive number")
    private int firstPlayerId;
    @Column(name = "\"Player2\"", nullable = false)
    @Positive(message = "secondPlayerId should be positive number")
    private int secondPlayerId;
    @Column(name = "\"Winner\"", nullable = false)
    @PositiveOrZero(message = "WinnerId must be positive number or 0")
    private int winnerId;

    public Match(int firstPlayerId, int secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }

    public Match(int firstPlayerId, int secondPlayerId, int winnerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.winnerId = winnerId;
    }
}
