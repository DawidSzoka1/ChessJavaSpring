package com.chessd.chess.utils;

import java.util.List;

public class Bishop extends Figure {
    public Bishop(String color, String position) {
        super("bishop", color, position);
    }

    @Override
    public List<String> availableMoves() {
        return List.of();
    }
}
