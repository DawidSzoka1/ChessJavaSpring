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

    public List<String> moveHorVer(int startRow, int startCol, boolean hor) {
        ArrayList<String> moves = new ArrayList<>();

        for (int direction = -1; direction <= 1; direction += 2) {
            int col = startCol;
            int row = startRow;
            while (true) {
                if (hor) {
                    col += direction;
                } else {
                    row += direction;
                }
                if (this.validPosition(row, col)) {
                    moves.add(Column.fromIndex(col).get().name() + row);
                } else {
                    break;
                }
            }
        }
        return moves;
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int startRow = this.getRow();
        int startCol = this.getCol();
        //moving horizontally
        moves.addAll(moveHorVer(startRow, startCol, true));
        //moving diagonally
        moves.addAll(moveHorVer(startRow, startCol, false));
        return moves;
    }
}


