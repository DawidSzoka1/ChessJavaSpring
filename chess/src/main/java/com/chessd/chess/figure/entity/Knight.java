package com.chessd.chess.figure.entity;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Knight extends Figure {
    public Knight(String color, Position position, Game game, User owner){
        super("knight",  color, position, game, owner);
    }
}
