package com.chessd.chess.event;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ValidateMoveEvent extends BaseChessEvent {
    public ValidateMoveEvent(Object source, Figure figure, String from, String to, Game game, HashMap<Position, Figure> board, String typeOfMove) {
        super(source, figure, from, to, game, board, typeOfMove);
    }
}
