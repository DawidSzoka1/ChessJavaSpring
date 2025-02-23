package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color, Position position, Game game) {

        super("queen", color, position, game);
    }
}
