package com.chessd.chess.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = FigureDeserializer.class)
public abstract class Figure {
    private String name;
    private String color;
    private String position;
    private String imageName;
    private List<String> moves;

    public Figure() {
    }

    public Figure(String name, String color, String position) {
        this.name = name;
        // color must be this format for page to work
        this.color = color.toLowerCase().startsWith("w") ? "W" : "B";
        this.position = position;
        this.moves = new ArrayList<>();
        this.setImageName();
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    //generic image name
    public void setImageName() {
        this.imageName = this.getColor() + "_" + this.name + ".png";
    }

    public String getImageName() {
        return imageName;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public void addMove(String move) {
        this.moves.add(move);
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

    abstract List<String> availableMoves();

    abstract void makeMove(String newPosition);

    @Override
    public String toString() {
        return "Figure{" +
                "name='" + name + '\'' +
                position +
                '}';
    }
}
