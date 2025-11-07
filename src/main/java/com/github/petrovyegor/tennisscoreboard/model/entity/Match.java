package com.github.petrovyegor.tennisscoreboard.model.entity;

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
    private long id;

    @ManyToOne
    @JoinColumn(name = "\"Player1\"", nullable = false)
    private Player firstPlayer;//TODO подумать нужно ли // При сохранении Match автоматически сохранится и Player @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "\"Player2\"", nullable = false)
    private Player secondPlayer;
    @ManyToOne
    @JoinColumn(name = "\"Winner\"", nullable = false)
    private Player winner;

    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }
}
