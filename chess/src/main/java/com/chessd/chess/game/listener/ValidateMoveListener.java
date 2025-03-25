package com.chessd.chess.game.listener;

import com.chessd.chess.game.event.KingSafetyEvent;
import com.chessd.chess.game.event.ValidateMoveEvent;
import com.chessd.chess.move.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class ValidateMoveListener {
    private final ApplicationEventPublisher eventPublisher;
    private final MoveService moveService;

    @Autowired
    public ValidateMoveListener(ApplicationEventPublisher eventPublisher, MoveService moveService) {
        this.eventPublisher = eventPublisher;
        this.moveService = moveService;
    }

    @EventListener
    public void onValidateMove(ValidateMoveEvent event) throws Exception {
        System.out.println("Uruchamiam ValidateListenera");
        moveService.isMoveValid(event.getFigure(), event.getTo(), event.getGame());
        eventPublisher.publishEvent(new KingSafetyEvent(event));
    }

}
