package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
        ArrayList<String> moves = new ArrayList<>();
        int[] horizontal = {-1, 1};
        int[] vertical = {-1, 1};
        for (int h : horizontal) {
            for (int v : vertical) {
                moves.addAll(generateMoves(this.getRow(), this.getCol(), v, h, board));
            }
        }
        return moves;
    }
}
