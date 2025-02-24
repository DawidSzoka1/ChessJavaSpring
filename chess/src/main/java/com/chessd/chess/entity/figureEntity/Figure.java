package com.chessd.chess.entity.figureEntity;


import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Position;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a generic chess figure (piece) with common properties and behaviors.
 * This is an abstract class that must be extended by specific chess piece types.
 */
@Slf4j
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

    @Column(name = "color", columnDefinition = "varchar(1) check (color in ('W', 'B'))")
    private String color;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    private Game game;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "moves")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "figure_moves", joinColumns = @JoinColumn(name = "figure_id"))
    private List<String> moves;

    @Column(name = "opponent", columnDefinition = "varchar(1) check(opponent in ('W', 'B'))")
    private String opponent;

    public Figure(String name, String color, Position position, String imageName, Game game) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.imageName = imageName;
        this.game = game;
        this.opponent = color.equals("W") ? "B" : "W";
    }

    public Figure(String name, String color, int row, int col, Game game) {
        this(
                name,
                color,
                Position.fromRowCol(row, col).orElseThrow(),
                color.toUpperCase() + "_" + name + ".png",
                game
        );
    }

    public Figure(String name, String color, Position position, Game game) {
        this(name, color, position, color.toUpperCase() + "_" + name + ".png", game);
    }

//    public static int[] convertStringPositionToRowColInt(String position) {
//        int[] tab = new int[2];
//        tab[0] = position.charAt(1) - '0';
//        tab[1] = com.chessd.chess.utils.Column.fromName(String.valueOf(position.charAt(0))).get().getIndex();
//        return tab;
//    }
//
//
//    public String[] validMove(int row, int col, Figure[][] board) {
//        String[] tab = new String[2];
//        tab[0] = "break";
//        if (!this.validRowCol(row, col)) {
//            return tab;
//        }
//        Figure posibleFigure = board[row][col];
//        if (posibleFigure != null && posibleFigure.getColor().equals(this.getColor())) {
//            return tab;
//        }
//        tab[0] = "break and add";
//        if (posibleFigure == null) {
//            tab[0] = "continue";
//        }
//        tab[1] = com.chessd.chess.utils.Column.fromIndex(col).get().name() + row;
//        return tab;
//    }
//
//    public List<String> generateMoves(int startRow, int startCol, int rowStep, int colStep, Figure[][] board){
//        ArrayList<String> moves = new ArrayList<>();
//        int newRow = startRow;
//        int newCol = startCol;
//        while (true) {
//            newCol += colStep;
//            newRow += rowStep;
//            String[] check = this.validMove(newRow, newCol, board);
//            if(check[0].equals("break")){
//                break;
//            }
//            moves.add(check[1]);
//            if(check[0].equals("break and add")){
//                break;
//            }
//        }
//        return moves;
//    }
//
//    public void makeMove(Position position, Figure[][] board) {
//        this.setPosition(position);
//        this.setMoves(this.availableMoves(board));
//    }
//
//    public boolean validRowCol(int row, int col) {
//        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
//    }
//
//    public boolean checkIfMoveIsValid(String newPosition, Figure[][] board) {
//        if(!this.getColor().equalsIgnoreCase(this.getGame().getNextMove())){
//            return false;
//        }
//        return this.checkIfMoveInAvailableMoves(newPosition, board);
//    }
//    public boolean checkIfMoveInAvailableMoves(String newPosition, Figure[][] board){
//        List<String> moves = this.getMoves();
//        if(moves == null || moves.isEmpty()){
//            moves = this.availableMoves(board);
//        }
//        return moves.contains(newPosition);
//    }

    @Override
    public String toString() {
        return name + '\'' +
                position + " " + id;
    }

    public int getRow() {
        return position.getRow();
    }
    public int getCol(){
        return position.getCol();
    }
}
