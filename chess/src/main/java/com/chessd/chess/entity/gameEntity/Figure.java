package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "figure")
public class Figure {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="game_id", referencedColumnName = "game_id", nullable = false),
            @JoinColumn(name = "position", referencedColumnName = "position")
    })
    private Game game;
}
