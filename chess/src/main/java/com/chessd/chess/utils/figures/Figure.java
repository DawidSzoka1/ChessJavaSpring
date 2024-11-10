package com.chessd.chess.utils.figures;

import java.awt.*;

public abstract class Figure {
    private int x;
    private int y;
    private boolean white;
    private boolean active;

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
}
