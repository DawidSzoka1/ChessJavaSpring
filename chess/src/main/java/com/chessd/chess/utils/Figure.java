package com.chessd.chess.utils;

import java.util.List;

public abstract class Figure {
    private String name;
    private String color;
    private String position;
    public Figure(){}
    public Figure(String name, String color, String position) {
        this.name = name;
        this.color = color;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    abstract List<String> avaibleMoves();
}
