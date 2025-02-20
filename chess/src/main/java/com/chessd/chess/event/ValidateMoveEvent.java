package com.chessd.chess.event;


import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import lombok.Getter;

@Getter
public class ValidateMoveEvent extends BaseChessEvent {
    public ValidateMoveEvent(Object source, Figure figure, String from, String to, Game game, Figure[][] board, String typeOfMove) {
        super(source, figure, from, to, game, board, typeOfMove);
    }

    public ValidateMoveEvent(BaseChessEvent event) {
        super(event);
    }
}
