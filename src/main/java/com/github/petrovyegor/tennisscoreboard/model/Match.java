package com.github.petrovyegor.tennisscoreboard.model;

import jakarta.persistence.*;
import lombok.*;

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
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "\"Player1\"", nullable = false)
    private int firstPlayerId;
    @Column(name = "\"Player2\"", nullable = false)
    private int secondPlayerId;
    @Column(name = "\"Winner\"", nullable = false)
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
