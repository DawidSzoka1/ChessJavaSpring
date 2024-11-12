package com.chessd.chess.utils.figures;


import com.chessd.chess.utils.Board;
import com.chessd.chess.utils.Player;

import java.util.List;

public abstract class Figure {
    private int x;
    private int y;
    private Player player;
    private boolean active;

    public Figure() {}
    public Figure(int x, int y, Player player, boolean active) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.active = active;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    abstract void move(Board board, int toX, int toY);
    abstract boolean isValidMove(Board board, int toX, int toY);
    abstract List<Integer[]> avaibleMoves();


    @Override
    public String toString() {
        return "Figure{" +
                "x=" + x +
                ", y=" + y +
                ", player=" + player +
                ", active=" + active +
                '}';
    }
}
