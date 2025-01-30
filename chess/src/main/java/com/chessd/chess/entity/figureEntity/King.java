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


    @Override
    public List<String> availableMoves(Figure[][] board) {
        List<String> moves = new ArrayList<>();
        int[] horizontal = {1, 0, -1};
        int[] vertical = {1, 0, -1};
        int newRow;
        int newCol;
        for(int row: vertical){
            for(int col: horizontal){
                newRow = this.getRow() + row;
                newCol = this.getCol() + col;
                if(this.validPosition(newRow, newCol)){
                    moves.add(Column.fromIndex(newCol).get().name() + newRow);
                }
            }
        }
        return moves;
    }
}
