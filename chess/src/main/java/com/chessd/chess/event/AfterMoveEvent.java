package com.chessd.chess.event;

import lombok.Getter;


@Getter
public class AfterMoveEvent extends BaseChessEvent {
    public AfterMoveEvent(BaseChessEvent event) {
        super(event);
    }
}
