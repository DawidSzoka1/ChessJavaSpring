package com.chessd.chess.utils.figures;

import com.chessd.chess.utils.Board;

public class Pawn extends Figure {
    public Pawn(int x, int y, boolean white, boolean active) {
        super(x, y, white, active);
    }

    @Override
    void move(Board board, int toX, int toY) {

    }
}
