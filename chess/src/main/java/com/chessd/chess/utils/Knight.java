package com.chessd.chess.utils;

import java.util.ArrayList;
import java.util.List;


public class Knight extends Figure {
    public Knight(String color, String position) {
        super("knight", color, position);
    }

    @Override
    public List<String> availableMoves() {
        ArrayList<String> moves = new ArrayList<>();
        int currentRow = this.getPosition().charAt(1) - '0';
        char currentCol = this.getPosition().charAt(0);
        int[] rowPossibilities = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colPossibilities = {1, -1, 1, -1, 2, -2, 2, -2};
        for (int i = 0; i < rowPossibilities.length; i++) {
            int newRow = currentRow + rowPossibilities[i];
            char newCol = (char) (currentCol + colPossibilities[i]);
            if (this.validPostion(newRow, newCol)) {
                moves.add("" + newCol + newRow);
            }
        }
        return moves;
    }
}
