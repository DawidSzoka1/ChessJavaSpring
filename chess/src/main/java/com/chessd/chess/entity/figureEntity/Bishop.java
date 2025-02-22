package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.MovesPattern;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Bishop extends Figure {
    public Bishop(String color, Position position, Game game) {
        super("bishop", color, position, game);
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        return MovesPattern.diagonalMoves(this, board);
    }
}
