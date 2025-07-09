package com.chessd.chess.game.listener;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.event.AfterMoveEvent;
import com.chessd.chess.game.event.EndGameEvent;
import com.chessd.chess.game.service.CheckService;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.move.entity.Move;
import com.chessd.chess.move.service.MoveService;
import com.chessd.chess.move.service.MoveUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;


@Slf4j
@Component
public class AfterMoveListener {
    private final CheckService checkService;
    private final MoveService moveService;
    private final MoveUpdateService moveUpdateService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final GameService gameService;

    @Autowired
    public AfterMoveListener(CheckService checkService, MoveService moveService, MoveUpdateService moveUpdateService, ApplicationEventPublisher applicationEventPublisher, GameService gameService) {
        this.checkService = checkService;
        this.moveService = moveService;
        this.moveUpdateService = moveUpdateService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.gameService = gameService;
    }


    @EventListener
    public void lookForCheck(AfterMoveEvent event) {
        System.out.println("Uruchamiam AFterMove");
        checkService.lookForChecks(event.getGame());
        this.saveMove(event);
        moveUpdateService.updateAffectedFigures(event.getFigure(), event.getBoard(), event.getFrom());
        applicationEventPublisher.publishEvent(new EndGameEvent(event));
    }

    private void saveMove(AfterMoveEvent event){
        Move move = new Move();
        Game game = event.getGame();
        move.setGameId(game);
        move.setUserId(event.getFigure().getOwnerId());
        move.setStartPosition(event.getFrom());
        move.setEndPosition(event.getTo());
        move.setTime(Timestamp.from(Instant.now()));
        move.setMoveId(game.getGameId().substring(0, 20) + move.getUserId().getId() + game.getMoveCount());
        game.setMoveCount(game.getMoveCount() + 1);
        moveService.save(move);
        gameService.save(game);
    }
}
