package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@NoArgsConstructor
public class King extends Figure {
    public King(String color, int row, int col, Game game) {
        super("king", color, row, col, game);
    }
    public King(String color, String position, Game game){
        super("king", color, position, game);
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
