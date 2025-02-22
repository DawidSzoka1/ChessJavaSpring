package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Column;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@NoArgsConstructor
public class King extends Figure {
    public King(String color, Position position, Game game){
        super("king", color, position, game);
    }

//    @Override
//    public List<String> availableMoves(Figure[][] board) {
//        List<String> moves = new ArrayList<>();
//        int[] horizontal = {1, 0, -1};
//        int[] vertical = {1, 0, -1};
//        int newRow;
//        int newCol;
//        for(int row: vertical){
//            for(int col: horizontal){
//                newRow = this.getPosition().getRow() + row;
//                newCol = this.getPosition().getCol() + col;
//                if(this.validRowCol(newRow, newCol)){
//                    Figure potentialF = board[newRow][newCol];
//                    if(potentialF == null || potentialF.getOpponent().equals(this.getColor())){
//                        moves.add(Column.fromIndex(newCol).get().name() + newRow);
//                    }
//                }
//            }
//        }
//        return moves;
//    }
}
