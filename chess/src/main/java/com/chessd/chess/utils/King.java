package com.chessd.chess.utils;

import java.util.*;


public class King extends Figure {

    public King() {
    }

    public King(String name, String color, String position) {
        super(name, color, position);
    }

    HashMap<Optional<Column>, Integer> directions() {
        HashMap<Optional<Column>, Integer> dire = new HashMap<>();
        Optional<Column> colO = Column.fromName(String.valueOf((this.getPosition().charAt(0))));
        int row = Integer.parseInt(String.valueOf(this.getPosition().charAt(1)));
        Column col;
        if (colO.isPresent()) {
            col = colO.get();
        } else {
            return null;
        }
        for (int i = 0; i < 3; i++) {
            double v = col.getIndex() + (i * Math.pow(-1, i));
            dire.put(Column.fromIndex((int) v), row + 1);
            dire.put(Column.fromIndex((int) v), row);
            dire.put(Column.fromIndex((int) v), row - 1);
        }
        return dire;
    }

    @Override
    List<String> avaibleMoves() {
        List<String> moves = new ArrayList<>();
        HashMap<Optional<Column>, Integer> directions = directions();
        

        return moves;
    }
}
