package com.chessd.chess.event;

import lombok.Getter;


@Getter
public class LookForCheckEvent  extends BaseChessEvent {
    public LookForCheckEvent(BaseChessEvent event) {
        super(event);
    }
}
