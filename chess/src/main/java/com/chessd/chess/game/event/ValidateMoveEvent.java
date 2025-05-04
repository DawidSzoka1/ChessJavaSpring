package com.chessd.chess.game.event;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class ValidateMoveEvent extends BaseChessEvent {
    private final User user;
    public ValidateMoveEvent(
            Object source,
            Figure figure,
            String from,
            String to,
            Game game,
            HashMap<Position, Figure> board,
            String typeOfMove,
            User user) {
        super(source, figure, from, to, game, board, typeOfMove);
        this.user = user;
    }
}
