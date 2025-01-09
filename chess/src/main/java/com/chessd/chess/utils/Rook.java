package com.chessd.chess.utils;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {
    public Rook(String color, String position) {
        super("rook", color, position);
    }

    @Override
    public List<String> availableMoves() {
        ArrayList<String> moves = new ArrayList<>();
        int startRow = this.getPosition().charAt(1) - '0';
        char startCol = this.getPosition().charAt(0);
        //moving horizontally
        for (int hor = -1; hor <= 1; hor += 2) {
            char col = startCol;
            while (true) {
                col += hor;
                if (this.validPostion(startRow, col)) {
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
                if (this.validPostion(row, startCol)) {
                    moves.add("" + startCol + row);
                } else {
                    break;
                }
            }
        }
        return moves;
    }
}


