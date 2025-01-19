package com.chessd.chess.utils;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@DiscriminatorValue("pawn")
@NoArgsConstructor
@Getter @Setter
public class Pawn extends Figure {
    private int direction;

    public Pawn(String color, String position) {
        super("pawn", color, position);
        direction = this.getColor().equals("W") ? 1 : -1;
    }
    public Pawn(String color, int row, int col){
        super("pawn", color, row, col);
        direction = this.getColor().equals("W") ? 1 : -1;
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int row = this.getRow();
        String col = Column.fromIndex(this.getCol()).get().name();
        moves.add(col + (row + this.direction));
        if ((row == 2 && this.getColor().equals("W")) ||
                (row == 7 && this.getColor().equals("B"))) {
            moves.add(col + (row + this.direction * 2));
        }
        if (board != null) {
            moves.addAll(availableTakes(board));
        }
        return moves;
    }

    public List<String> availableTakes(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int startRow =  this.getRow();
        char startCol = Column.fromIndex(this.getCol()).get().name().charAt(0);
        for (int i = -1; i <= 1; i += 2) {
            int newRow = startRow + this.direction;
            char newCol = (char) (startCol + i);
            if (this.validPosition(newRow, newCol)) {
                Figure figure = board[newRow-2][Column.fromName(String.valueOf(newCol)).get().getIndex()];
                if(startCol == 'e'){
                    System.out.println(Arrays.deepToString(board));
                    System.out.println(Column.fromName("d").get());
                    System.out.println(""+newCol+newRow);
                }
                if (figure != null && !figure.getColor().equals(this.getColor())) {
                    moves.add(Column.fromIndex(figure.getCol()).get().name() + figure.getRow());
                }
            }
        }
        return moves;
    }
}
