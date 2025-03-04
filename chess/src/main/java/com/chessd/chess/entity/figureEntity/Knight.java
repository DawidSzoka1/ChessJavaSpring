package com.chessd.chess.entity.figureEntity;

import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.Position;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Knight extends Figure {
    public Knight(String color, Position position, Game game){
        super("knight",  color, position, game);
    }
}
