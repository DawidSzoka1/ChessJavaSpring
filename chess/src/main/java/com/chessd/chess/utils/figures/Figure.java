package com.chessd.chess.utils.figures;


import com.chessd.chess.utils.Board;

public abstract class Figure {
    private int x;
    private int y;
    private boolean white;
    private boolean active;

    public Figure() {}
    public Figure(int x, int y, boolean white, boolean active) {
        this.x = x;
        this.y = y;
        this.white = white;
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

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    abstract void move(Board board, int toX, int toY);


    @Override
    public String toString() {
        return "Figure{" +
                "x=" + x +
                ", y=" + y +
                ", white=" + white +
                ", active=" + active +
                '}';
    }
}
