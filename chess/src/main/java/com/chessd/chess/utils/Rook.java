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
        int[] newRow = new int[4];
        char[] newCol = new char[4];
        for (int i = 0; i < 8; i++) {
            //moving horizontally
            for (int hor = 0; hor < 2; hor++) {
                newRow[hor] = startRow;
                newCol[hor] = (char) (startCol + ((int) Math.pow(-1, hor) * i));
                if (this.validPostion(newRow[hor], newCol[hor])) {
                    moves.add("" + newCol[hor] + newRow[hor]);
                }
            }
            //moving vertically
            for (int ver = 2; ver < 4; ver++) {
                newRow[ver] = startRow + ((int) Math.pow(-1, ver) * i);
                newCol[ver] = startCol;
                if (this.validPostion(newRow[ver], newCol[ver])) {
                    moves.add("" + newCol[ver] + newRow[ver]);
                }
            }
        }
        return moves;
    }

}
