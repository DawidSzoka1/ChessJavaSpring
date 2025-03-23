package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Bishop extends Figure {
    public Bishop(String color, Position position, Game game) {
        super("bishop", color, position, game);
    }
}
