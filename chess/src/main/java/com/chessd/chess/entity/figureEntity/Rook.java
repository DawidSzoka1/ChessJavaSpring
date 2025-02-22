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
public class Rook extends Figure {
    public Rook(String color, Position position, Game game) {
        super("rook", color, position, game);
    }

//    @Override
//    public List<String> availableMoves(Figure[][] board) {
//        ArrayList<String> moves = new ArrayList<>();
//        //moving horizontally
//        moves.addAll(MovesPattern.movesHorVer(this, board, true));
//        //moving diagonally
//        moves.addAll(MovesPattern.movesHorVer(this, board, false));
//        return moves;
//    }
}


