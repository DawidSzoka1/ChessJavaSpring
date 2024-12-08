package com.chessd.chess.utils;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure{
    private boolean first = true;
    private int direction;
    public Pawn(){}

    public Pawn(String color, String position) {
        super(color+"p", color, position);
        direction = this.getColor().equals("W") ? 1 : -1;
        this.setMoves(avaibleMoves());
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    List<String> avaibleMoves() {
        List<String> moves = new ArrayList<>();
        int row = Integer.parseInt(String.valueOf(this.getPosition().charAt(1)));
        moves.add(String.valueOf(this.getPosition().charAt(0)) + (row + this.direction));
        if(this.first){
            moves.add(String.valueOf(this.getPosition().charAt(0)) + (row + this.direction * 2));
        }
        return moves;
    }
}
