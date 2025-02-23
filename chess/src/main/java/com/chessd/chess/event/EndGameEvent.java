package com.chessd.chess.event;

import lombok.Getter;

@Getter
public class EndGameEvent extends BaseChessEvent {
    public EndGameEvent(BaseChessEvent event) {
        super(event);
    }
}
