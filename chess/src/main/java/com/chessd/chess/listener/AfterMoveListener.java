package com.chessd.chess.listener;

import com.chessd.chess.event.AfterMoveEvent;
import com.chessd.chess.event.EndGameEvent;
import com.chessd.chess.service.CheckService;
import com.chessd.chess.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AfterMoveListener {
    private final CheckService checkService;
    private final MoveService moveService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AfterMoveListener(CheckService checkService, MoveService moveService, ApplicationEventPublisher applicationEventPublisher) {
        this.checkService = checkService;
        this.moveService = moveService;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @EventListener
    public void lookForCheck(AfterMoveEvent event) {
        checkService.lookForChecks(event.getGame());
        if(event.getGame().getCheckStatus().equals("N")){
            moveService.updateFiguresMove(event.getGame());
        }
        applicationEventPublisher.publishEvent(new EndGameEvent(event));
    }
}
