package com.chessd.chess.game.event;

import lombok.Getter;

@Getter
public class EndGameEvent extends BaseChessEvent {
    public EndGameEvent(BaseChessEvent event) {
        super(event);
    }
}
