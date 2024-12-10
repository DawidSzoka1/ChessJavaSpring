package com.chessd.chess.utils;

import java.util.List;


public class Knight extends Figure{
    public Knight(String color, String position) {
        super("knight", color, position);
    }
    //TODO: moves
    @Override
    List<String> availableMoves() {
        return List.of();
    }

    @Override
    void makeMove(String newPosition) {

    }
}
