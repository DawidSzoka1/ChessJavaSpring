package com.chessd.chess.event;

import lombok.Getter;

@Getter
public class ProcessMoveEvent extends BaseChessEvent {
    public ProcessMoveEvent(BaseChessEvent event) {
        super(event);
    }
}
