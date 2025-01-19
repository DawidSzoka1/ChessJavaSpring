package com.chessd.chess.utils;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@DiscriminatorValue("king")
@NoArgsConstructor
public class King extends Figure {
    public King(String color, int row, int col) {
        super("king", color, row, col);
    }
    public King(String color, String position){
        super("king", color, position);
    }
    HashMap<Optional<Column>, Integer> directions() {
        HashMap<Optional<Column>, Integer> dire = new HashMap<>();
        Optional<Column> colO = Column.fromIndex(this.getCol());
        int row = this.getRow();
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
    public List<String> availableMoves(Figure[][] board) {
        List<String> moves = new ArrayList<>();
        HashMap<Optional<Column>, Integer> directions = directions();
        // TODO: moves for king to finish

        return moves;
    }
}
