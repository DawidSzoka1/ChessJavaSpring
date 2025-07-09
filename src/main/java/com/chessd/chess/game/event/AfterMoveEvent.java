package com.chessd.chess.game.event;

import lombok.Getter;


@Getter
public class AfterMoveEvent extends BaseChessEvent {
    public AfterMoveEvent(BaseChessEvent event) {
        super(event);
    }
}
