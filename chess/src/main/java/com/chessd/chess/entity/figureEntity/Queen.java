package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.MovesPattern;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color, Position position, Game game) {

        super("queen", color, position, game);
    }

//    @Override
//    public List<String> availableMoves(Figure[][] board) {
//        ArrayList<String> moves = new ArrayList<>();
//        moves.addAll(MovesPattern.diagonalMoves(this, board));
//        moves.addAll(MovesPattern.movesHorVer(this, board, true));
//        moves.addAll(MovesPattern.movesHorVer(this, board, false));
//        return moves;
//    }

}
