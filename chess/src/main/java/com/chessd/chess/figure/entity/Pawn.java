package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Pawn extends Figure {
    @jakarta.persistence.Column(name = "direction")
    private int direction;

    public Pawn(String color, Position position, Game game) {
        super("pawn", color, position, game);
        direction = this.getColor().equals("W") ? 1 : -1;
    }
    public Pawn(String color, Position position, Game game, List<String> moves) {
        super("pawn", color, position, game);
        direction = this.getColor().equals("W") ? 1 : -1;
        this.setMoves(moves);
    }

}
