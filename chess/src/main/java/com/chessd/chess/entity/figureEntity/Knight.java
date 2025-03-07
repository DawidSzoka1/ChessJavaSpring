package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Knight extends Figure {
    public Knight(String color, int row, int col, Game game) {
        super("knight", color, row, col, game);
    }
    public Knight(String color, String position, Game game){
        super("knight",  color, position, game);
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
            if (this.validRowCol(newRow, newCol)) {
                moves.add(Column.fromIndex(newCol).get().name() + newRow);
            }
        }
        return moves;
    }
}
