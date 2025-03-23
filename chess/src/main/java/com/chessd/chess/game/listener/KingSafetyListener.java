package com.chessd.chess.game.listener;

import com.chessd.chess.game.event.KingSafetyEvent;
import com.chessd.chess.game.event.ProcessMoveEvent;
import com.chessd.chess.game.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class KingSafetyListener {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CheckService checkService;

    @Autowired
    public KingSafetyListener(ApplicationEventPublisher applicationEventPublisher, CheckService checkService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.checkService = checkService;
    }

    @EventListener
    public void onKingSafety(KingSafetyEvent event) throws Exception {
        System.out.println("Uruchamiam KingSafListenera");
        if (!checkService.isKingSafeAfterMove(event.getFigure(), event.getTo(), event.getGame())) {
            throw new Exception("King is under attack after move");
        }
        applicationEventPublisher.publishEvent(new ProcessMoveEvent(event));
    }

}
