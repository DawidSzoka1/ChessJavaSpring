package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
public class Queen extends Figure {
    public Queen(String color, String position, Game game) {

        super("queen", color, position, game);
    }

    public Queen(String color, int row, int col, Game game) {
        super("queen", color, row, col, game);
    }


    //TODO: moves for Queen
    @Override
    public List<String> availableMoves(Figure[][] board) {
        return List.of();
    }

}
