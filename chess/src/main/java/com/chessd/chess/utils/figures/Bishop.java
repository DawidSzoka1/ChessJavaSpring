package com.chessd.chess.utils.figures;

import com.chessd.chess.utils.Board;
import com.chessd.chess.utils.Player;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Figure {

    public Bishop(int x, int y, Player player, boolean active) {
        super(x, y, player, active);
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
        List<Integer[]> moves = new ArrayList<>();

        return null;
    }
}
