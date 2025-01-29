package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color, String position, Game game) {

        super("queen", color, position, game);
    }

    public Queen(String color, int row, int col, Game game) {
        super("queen", color, row, col, game);
    }


    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        moves.addAll(MovesPattern.diagonalMoves(this, board));
        moves.addAll(MovesPattern.movesHorVer(this, board, true));
        moves.addAll(MovesPattern.movesHorVer(this, board, false));
        return moves;
    }

}
