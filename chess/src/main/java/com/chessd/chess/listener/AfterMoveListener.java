package com.chessd.chess.listener;

import com.chessd.chess.event.AfterMoveEvent;
import com.chessd.chess.event.EndGameEvent;
import com.chessd.chess.service.game.CheckService;
import com.chessd.chess.service.game.MoveService;
import com.chessd.chess.service.MoveUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AfterMoveListener {
    private final CheckService checkService;
    private final MoveService moveService;
    private final MoveUpdateService moveUpdateService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AfterMoveListener(CheckService checkService, MoveService moveService, MoveUpdateService moveUpdateService, ApplicationEventPublisher applicationEventPublisher) {
        this.checkService = checkService;
        this.moveService = moveService;
        this.moveUpdateService = moveUpdateService;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @EventListener
    public void lookForCheck(AfterMoveEvent event) {
        System.out.println("Uruchamiam AFterMove");
        checkService.lookForChecks(event.getGame());
        moveUpdateService.updateAffectedFigures(event.getFigure(), event.getBoard(), event.getFrom());
        applicationEventPublisher.publishEvent(new EndGameEvent(event));
    }
}
