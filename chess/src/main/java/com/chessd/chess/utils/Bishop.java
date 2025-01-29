package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Bishop extends Figure {
    public Bishop(String color, String position, Game game) {
        super("bishop", color, position, game);
    }

    public Bishop(String color, int row, int col, Game game) {
        super("bishop", color, row, col, game);
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        return MovesPattern.diagonalMoves(this, board);
    }
}
