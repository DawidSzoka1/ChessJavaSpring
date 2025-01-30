package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Pawn extends Figure {
    @jakarta.persistence.Column(name = "direction")
    private int direction;

    public Pawn(String color, String position, Game game) {
        super("pawn", color, position, game);
        direction = this.getColor().equals("W") ? 1 : -1;
    }
    public Pawn(String color, int row, int col, Game game){
        super("pawn", color, row, col, game);
        direction = this.getColor().equals("W") ? 1 : -1;
    }

    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        int row = this.getRow();
        String col = Column.fromIndex(this.getCol()).get().name();
        moves.add(col + (row + this.direction));
        if ((row == 1 && this.getColor().equals("W")) ||
                (row == 6 && this.getColor().equals("B"))) {
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
        int startCol = this.getCol();
        for (int i = -1; i <= 1; i += 2) {
            int newRow = startRow + this.direction;
            int newCol =  startCol + i;
            if (this.validRowCol(newRow, newCol)) {
                Figure toTake = board[newRow][newCol];
                if(toTake != null && !toTake.getColor().equals(this.getColor())){
                    moves.add(toTake.getPosition());
                }
            }
        }
        return moves;
    }
}
