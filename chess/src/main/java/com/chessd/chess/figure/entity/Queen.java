package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color, Position position, Game game) {

        super("queen", color, position, game);
    }
}
