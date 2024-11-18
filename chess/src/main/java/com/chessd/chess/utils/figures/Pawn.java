package com.chessd.chess.utils.figures;

import com.chessd.chess.utils.Board;
import com.chessd.chess.utils.Player;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    private boolean first;

    public Pawn(int x, int y, Player player, boolean active) {
        super(x, y, player, active, "pawn");
        first = true;
    }

    @Override
    void move(Board board, int toX, int toY) {
        if(this.isActive()){
            System.out.println("nie zyje");
        }
        else if(isValidMove(board, toX, toY)) {
            this.setX(toX);
            this.setY(toY);
            board.setMoveWhite(!board.getMoveWhite());
        }
    }

    @Override
    boolean isValidMove(Board board, int toX, int toY) {
        if(this.getPlayer().isWhite()  != board.getMoveWhite()){
            return false;
        }
        if(toX >= 8 || toY >= 8 || toX < 0 || toY < 0 ){
            return false;
        }
        if(board.getBoard()[toX][toY] != null && board.getBoard()[toX][toY].getPlayer().equals(this.getPlayer())){
            return false;
        }
        if(board.getBoard()[toX][toY].getPlayer() != null) {
//            to do!
            return false;
        }
        if(toX != this.getX()){
            return false;
        }
        int direction = this.getPlayer().isWhite() ? 1 : -1;
        if( this.first && (toY == this.getY() + direction || toY == this.getY() + direction * 2)) {
            return true;
        }
        if(toY == this.getY() + direction){
            return true;
        }
        return false;
    }

    @Override
    List<Integer[]> movesPattern() {
        int direction = this.getPlayer().isWhite() ? 1 : -1;
        List<Integer[]> moves = new ArrayList<>();
        moves.add(new Integer[]{this.getX(), this.getY() + direction});
        if(this.first){
            moves.add(new Integer[]{this.getX(), this.getY() + direction * 2});
        }

        return moves;
    }
}
