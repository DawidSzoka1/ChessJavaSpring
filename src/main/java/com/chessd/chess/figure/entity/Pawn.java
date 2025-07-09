package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;
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
    public Pawn(String color, Position position, Game game, List<String> moves, User owner) {
        super("pawn", color, position, game, owner);
        direction = this.getColor().equals("W") ? 1 : -1;
        this.setMoves(moves);
    }
}
