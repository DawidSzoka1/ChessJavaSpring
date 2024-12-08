package com.chessd.chess.utils;

import java.util.List;

public class Bishop extends Figure{
    public Bishop() {
    }

    public Bishop(String color, String position) {
        super(color+"b", color, position);
    }

    @Override
    List<String> availableMoves() {
        return List.of();
    }

    @Override
    void makeMove(String newPosition) {

    }
}
