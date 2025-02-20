package com.chessd.chess.event;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public abstract class BaseChessEvent extends ApplicationEvent {
    private final Figure figure;
    private final String from;
    private final String to;
    private final Game game;
    private final Figure[][] board;
    private final String typeOfMove;

    public BaseChessEvent(Object source, Figure figure, String from, String to, Game game, Figure[][] board, String typeOfMove) {
        super(source);
        this.figure = figure;
        this.from = from;
        this.to = to;
        this.game = game;
        this.board = board;
        this.typeOfMove = typeOfMove;
    }

    public BaseChessEvent(BaseChessEvent event) {
        this(event.getSource(), event.getFigure(), event.getFrom(), event.getTo(), event.getGame(), event.getBoard(), event.getTypeOfMove());
    }
}
