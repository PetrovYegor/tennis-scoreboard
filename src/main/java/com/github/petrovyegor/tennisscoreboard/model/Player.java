package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Player {
    private int id;
    private String name;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }
}
