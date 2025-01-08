package com.chessd.chess.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    private static final Logger log = LoggerFactory.getLogger(Pawn.class);
    private int direction;

    public Pawn(String color, String position) {
        super("pawn", color, position);
        direction = this.getColor().equals("W") ? 1 : -1;
        this.setMoves(availableMoves());
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public List<String> availableMoves() {
        List<String> moves = new ArrayList<>();
        int row = Integer.parseInt(String.valueOf(this.getPosition().charAt(1)));
        moves.add(String.valueOf(this.getPosition().charAt(0)) + (row + this.direction));
        if ((row == 2 && this.getColor().equals("W")) ||
                (row == 7 && this.getColor().equals("B"))) {
            moves.add(String.valueOf(this.getPosition().charAt(0)) + (row + this.direction * 2));
        }
        return moves;
    }
}
