package com.chessd.chess.utils;

import java.util.List;

public class Queen extends Figure {
    public Queen() {
    }

    public Queen(String color, String position) {
        super(color + "q", color, position);
    }

    //TODO: moves for Queen
    @Override
    List<String> availableMoves() {
        return List.of();
    }

    @Override
    void makeMove(String newPosition) {

    }
}
