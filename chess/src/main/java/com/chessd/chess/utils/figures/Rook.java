package com.chessd.chess.utils.figures;

import com.chessd.chess.utils.Board;
import com.chessd.chess.utils.Player;

import java.util.List;

public class Rook extends Figure {
    public Rook(int x, int y, Player player, boolean active) {
        super(x, y, player, active, "rook");
    }

    @Override
    void move(Board board, int toX, int toY) {

    }

    @Override
    boolean isValidMove(Board board, int toX, int toY) {
        return false;
    }

    @Override
    List<Integer[]> avaibleMoves() {
        return null;
    }
}
