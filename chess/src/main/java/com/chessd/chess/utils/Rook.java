package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
public class Rook extends Figure {
    public Rook(String color, String position, Game game) {
        super("rook", color, position, game);
    }

    public Rook(String color, int row, int col, Game game) {
        super("rook", color, row, col, game);
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int startRow = this.getRow();
        char startCol = Column.fromIndex(this.getCol()).get().name().charAt(0);
        //moving horizontally
        for (int hor = -1; hor <= 1; hor += 2) {
            char col = startCol;
            while (true) {
                col += hor;
                if (this.validPosition(startRow, col)) {
                    moves.add("" + col + startRow);
                } else {
                    break;
                }
            }
        }
        //moving vertically
        for (int ver = -1; ver <= 1; ver += 2) {
            int row = startRow;
            while (true) {
                row += ver;
                if (this.validPosition(row, startCol)) {
                    moves.add("" + startCol + row);
                } else {
                    break;
                }
            }
        }
        return moves;
    }
}


