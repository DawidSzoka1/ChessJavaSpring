package com.chessd.chess.listener;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.event.AfterMoveEvent;
import com.chessd.chess.event.ProcessMoveEvent;
import com.chessd.chess.service.game.MoveService;
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
        System.out.println("Uruchamiam ProcessMoveListenera");
        Figure figure = event.getFigure();
        String to = event.getTo();
        Game game = event.getGame();
        if (event.getTypeOfMove().equals("take")) {
            moveService.handleTakingFigure(figure, to, game);
        }
        System.out.println(figure.getPosition());
        moveService.executeMove(figure, to, game);
        System.out.println(figure.getPosition());
        applicationEventPublisher.publishEvent(new AfterMoveEvent(event));
    }
}
