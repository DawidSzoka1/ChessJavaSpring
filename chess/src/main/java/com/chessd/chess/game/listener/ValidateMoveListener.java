package com.chessd.chess.game.listener;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.event.KingSafetyEvent;
import com.chessd.chess.game.event.ValidateMoveEvent;
import com.chessd.chess.game.exception.InvalidMove;
import com.chessd.chess.game.exception.InvalidPlayer;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.user.entity.User;
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
        if(!correctUser(event.getUser(), event.getGame(), event.getFigure())){
            throw new InvalidPlayer("Ta figura nie jest twoja chlopie");
        }
        if(!moveService.isMoveValid(event.getFigure(), event.getTo(), event.getGame())){
            throw new InvalidMove("Ta figura nie ma takiego ruchu lub nie jest teraz dozwolony");
        }
        eventPublisher.publishEvent(new KingSafetyEvent(event));
    }

    private boolean correctUser(User user, Game game, Figure figure){
        String white = game.getWhite().getUserName();
        String black = game.getBlack().getUserName();
        String userName = user.getUserName();
        String owner = figure.getOwnerId().getUserName();
        return (userName.equals(white) && userName.equals(owner)) || (userName.equals(black) && userName.equals(owner));
    }

}
