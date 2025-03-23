package com.chessd.chess.game.event;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ValidateMoveEvent extends BaseChessEvent {
    public ValidateMoveEvent(Object source, Figure figure, String from, String to, Game game, HashMap<Position, Figure> board, String typeOfMove) {
        super(source, figure, from, to, game, board, typeOfMove);
    }
}
