package com.github.petrovyegor.tennisscoreboard.model;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    public Player(String name){
        this.name = name;
    }
}
