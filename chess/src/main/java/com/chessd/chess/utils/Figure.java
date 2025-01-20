package com.chessd.chess.utils;


import com.chessd.chess.entity.Game;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a generic chess figure (piece) with common properties and behaviors.
 * This is an abstract class that must be extended by specific chess piece types.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
public abstract class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "`row`")
    private int row;

    @Column(name = "col")
    private int col;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    private Game game;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "position")
    private String position;

    @Column(name = "moves")
    @Convert(converter = MovesConverter.class)
    private List<String> moves;

    public Figure(String name, String color, String position, String imageName, Game game) {
        int[] tab = convertStringPositionToRowColInt(position);
        this.name = name;
        this.color = color;
        this.position = position;
        this.row = tab[0];
        this.col = tab[1];
        this.imageName = imageName;
        this.game = game;

    }

    public Figure(String name, String color, int row, int col, Game game) {
        this(
                name,
                color,
                com.chessd.chess.utils.Column.fromIndex(col).get().name() + row,
                color.toUpperCase() + "_" + name + ".png",
                game
        );
    }

    public Figure(String name, String color, String position, Game game) {
        this(name, color, position, color.toUpperCase() + "_" + name + ".png", game);
    }

    public static int[] convertStringPositionToRowColInt(String position) {
        int[] tab = new int[2];
        tab[0] = position.charAt(1) - '0';
        tab[1] = com.chessd.chess.utils.Column.fromName(String.valueOf(position.charAt(0))).get().getIndex();
        return tab;
    }

    /**
     * Abstract method to calculate the available moves for the figure.
     * Must be implemented by subclasses.
     *
     * @return a list of valid moves as strings.
     */
    public abstract List<String> availableMoves(Figure[][] board);


    public void makeMove(String position, Figure[][] board) {
        int[] tab = convertStringPositionToRowColInt(position);
        this.setPosition(position);
        this.setRow(tab[0]);
        this.setCol(tab[1]);
        this.setMoves(this.availableMoves(board));
        System.out.println("Ruchy dla figury po ruchu " + this.name + " " + this.row + this.col);
        System.out.println(availableMoves(board));

    }

    public boolean validPosition(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    public boolean checkIfMoveIsValid(String newPosition, Figure[][] board) {
        for (String move : this.availableMoves(board)) {
            if (move.equals(newPosition)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name + '\'' +
                row + ' ' + col + " " + id;
    }
}
