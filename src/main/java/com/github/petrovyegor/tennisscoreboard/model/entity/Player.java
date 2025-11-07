package com.github.petrovyegor.tennisscoreboard.model.entity;

import jakarta.persistence.*;
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
    private long id;

    @Column(name = "\"Name\"", unique = true, nullable = false)
    private String name;

    public Player(String name) {
        this.name = name;
    }

//    public Player(int id, String name) {//нужен был для теста
//        this.id = id;
//        this.name = name;
//    }
}
