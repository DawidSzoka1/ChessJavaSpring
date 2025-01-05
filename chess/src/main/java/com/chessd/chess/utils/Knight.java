package com.chessd.chess.utils;

import java.util.List;


public class Knight extends Figure {
    public Knight(String color, String position) {
        super("knight", color, position);
    }

    //TODO: moves
    @Override
    public List<String> availableMoves() {
        return List.of();
    }
}
