package com.chessd.chess.game.event;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.utils.GameResult;
import lombok.Getter;

@Getter
public class EndGameEvent extends BaseChessEvent {
    private GameResult gameResult;

    public EndGameEvent(BaseChessEvent event) {
        super(event);
    }

    public EndGameEvent(Object source, Game game, GameResult gameResult) {
        super(source, game);
        this.gameResult = gameResult;
    }
}
