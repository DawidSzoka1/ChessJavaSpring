package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
public class Rook extends Figure {
    public Rook(String color, String position, Game game) {
        super("rook", color, position, game);
    }

    public Rook(String color, int row, int col, Game game) {
        super("rook", color, row, col, game);
    }


    @Override
    public List<String> availableMoves(Figure[][] board) {
        ArrayList<String> moves = new ArrayList<>();
        //moving horizontally
        moves.addAll(MovesPattern.movesHorVer(this, board, true));
        //moving diagonally
        moves.addAll(MovesPattern.movesHorVer(this, board, false));
        return moves;
    }
}


