package com.chessd.chess.utils;

import java.util.List;
//TODO: whole class Rook
public class Rook extends Figure{
    public Rook(String color, String position) {
        super("rook", color, position);
    }

    @Override
    List<String> availableMoves() {
        return List.of();
    }

    @Override
    void makeMove(String newPosition) {

    }
}
