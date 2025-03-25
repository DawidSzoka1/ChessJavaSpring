package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Rook extends Figure {
    public Rook(String color, Position position, Game game) {
        super("rook", color, position, game);
    }
}


