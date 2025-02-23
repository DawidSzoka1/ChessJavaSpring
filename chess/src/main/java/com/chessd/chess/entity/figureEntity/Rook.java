package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Rook extends Figure {
    public Rook(String color, Position position, Game game) {
        super("rook", color, position, game);
    }
}


