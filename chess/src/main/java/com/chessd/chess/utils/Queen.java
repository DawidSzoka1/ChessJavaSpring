package com.chessd.chess.utils;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("queen")
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color) {
        //default values that will work
        //if changed you need to change images name as well
        super("queen", color, "d" + (color.startsWith("W") ? 1 : 8));
    }

    public Queen(String color, String position) {

        super("queen", color, position);
    }

    public Queen(String color, int row, int col) {
        super("queen", color, row, col);
    }


    //TODO: moves for Queen
    @Override
    public List<String> availableMoves(Figure[][] board) {
        return List.of();
    }

}
