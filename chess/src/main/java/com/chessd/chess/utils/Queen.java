package com.chessd.chess.utils;

import java.util.List;

public class Queen extends Figure {
    public Queen(String color) {
        //default values that will work
        //if changed you need to change images name as well
        super("queen", color, "d" + (color.startsWith("W") ? 1 : 8));
    }

    public Queen(String color, String position) {
        super("queen", color, position);
    }


    //TODO: moves for Queen
    @Override
    public List<String> availableMoves() {
        return List.of();
    }

}
