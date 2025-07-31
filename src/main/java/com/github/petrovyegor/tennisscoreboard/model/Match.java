package com.github.petrovyegor.tennisscoreboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Matches")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Positive(message = "ID should be positive number")
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "player1", nullable = false)
    @Positive(message = "firstPlayerId should be positive number")
    private int firstPlayerId;
    @Column(name = "player2", nullable = false)
    @Positive(message = "secondPlayerId should be positive number")
    private int secondPlayerId;
    @Column(name = "winner", nullable = false)
    @Positive(message = "winnerId should be positive number")
    private int winnerId;

    public Match(int firstPlayerId, int secondPlayerId) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }
}
