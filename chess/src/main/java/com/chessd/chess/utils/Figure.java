package com.chessd.chess.utils;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

/**
 * Represents a generic chess figure (piece) with common properties and behaviors.
 * This is an abstract class that must be extended by specific chess piece types.
 */
@JsonDeserialize(using = FigureDeserializer.class)
public abstract class Figure {
    private String name;
    private String color;
    private String position;
    private String imageName;
    private List<String> moves;

    public Figure() {
    }

    /**
     * Constructs a {@code Figure} with the specified attributes.
     *
     * @param name     the name of the figure (e.g., "Pawn", "Knight").
     * @param color    the color of the figure ("W" for white, "B" for black).
     * @param position the initial position of the figure on the chessboard.
     */
    public Figure(String name, String color, String position) {
        this.name = name;
        // Ensures the color is in the correct format ("W" or "B").
        this.color = color.toLowerCase().startsWith("w") ? "W" : "B";
        this.position = position;
        this.moves = this.availableMoves(null);
        this.setImageName();
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Automatically sets the image name based on the figure's color and name.
     */
    public void setImageName() {
        this.imageName = this.getColor() + "_" + this.name + ".png";
    }

    public String getImageName() {
        return imageName;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the figure and updates its available moves.
     *
     * @param position the new position of the figure.
     */
    public void setPosition(String position, Figure[][] board) {
        this.position = position;
        this.setMoves(this.availableMoves(board));
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

    /**
     * Abstract method to calculate the available moves for the figure.
     * Must be implemented by subclasses.
     *
     * @return a list of valid moves as strings.
     */
    public abstract List<String> availableMoves(Figure[][] board);
    /**
     * Attempts to move the figure to a new position if the move is valid.
     *
     * @param newPosition the desired new position of the figure.
     * @return {@code true} if the move is valid and successful, {@code false} otherwise.
     */
    public boolean makeMove(String newPosition, Figure[][] board) {
        System.out.println("Ruchy dla figury na pozycji " + position);
        System.out.println(availableMoves(board));
        for (String move : availableMoves(board)) {
            if (move.equals(newPosition)) {
                this.setPosition(newPosition, board);
                return true;
            }
        }
        return false;
    }
    public boolean validPosition(int row, char col){
        return row >= 1 && row <= 8 && col >= 'a' && col <= 'h';
    }

    @Override
    public String toString() {
        return "Figure{" +
                "name='" + name + '\'' +
                position +
                '}';
    }
}
