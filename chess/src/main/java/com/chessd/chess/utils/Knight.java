package com.chessd.chess.utils;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("knight")
@NoArgsConstructor
public class Knight extends Figure {
    public Knight(String color, int row, int col) {
        super("knight", color, row, col);
    }
    public Knight(String color, String position){
        super("knight",  color, position);
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int currentRow = this.getRow();
        int currentCol = this.getCol();
        int[] rowPossibilities = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colPossibilities = {1, -1, 1, -1, 2, -2, 2, -2};
        for (int i = 0; i < rowPossibilities.length; i++) {
            int newRow = currentRow + rowPossibilities[i];
            int newCol = currentCol + colPossibilities[i];
            if (this.validPosition(newRow, newCol)) {
                moves.add("" + newCol + newRow);
            }
        }
        return moves;
    }
}
