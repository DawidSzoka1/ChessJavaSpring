package com.chessd.chess.listener;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.event.LookForCheckEvent;
import com.chessd.chess.event.ProcessMoveEvent;
import com.chessd.chess.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ProcessMoveListener {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final MoveService moveService;

    @Autowired
    public ProcessMoveListener(ApplicationEventPublisher applicationEventPublisher, MoveService moveService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.moveService = moveService;
    }

    @EventListener
    public void onProcessMove(ProcessMoveEvent event) throws Exception {
        Figure figure = event.getFigure();
        String to = event.getTo();
        Game game = event.getGame();
        if (event.getTypeOfMove().equals("take")) {
            moveService.handleTakingFigure(figure, to, game);
        }
        moveService.executeMove(figure, to, game);
        applicationEventPublisher.publishEvent(new LookForCheckEvent(event));
    }


}
