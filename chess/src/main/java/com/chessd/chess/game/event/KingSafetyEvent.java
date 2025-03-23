package com.chessd.chess.game.event;

import lombok.Getter;

@Getter
public class KingSafetyEvent extends BaseChessEvent {
    public KingSafetyEvent(BaseChessEvent event) {
        super(event);
    }
}
