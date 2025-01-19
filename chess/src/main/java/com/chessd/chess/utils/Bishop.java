package com.chessd.chess.utils;

import java.util.List;

public class Bishop extends Figure {
    public Bishop(String color, String position) {
        super("bishop", color, position);
    }

    public Bishop(String color, int row, int col) {
        super("bishop", color, row, col);
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        return List.of();
    }
}
