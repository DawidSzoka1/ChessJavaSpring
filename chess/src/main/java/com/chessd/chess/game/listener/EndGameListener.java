package com.chessd.chess.game.listener;

import com.chessd.chess.game.event.EndGameEvent;
import com.chessd.chess.game.service.EndGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EndGameListener {
    private final EndGameService endGameService;

    @Autowired
    public EndGameListener(EndGameService endGameService) {
        this.endGameService = endGameService;
    }

    @EventListener
    public void onEndGame(EndGameEvent event){
        System.out.println("Uruchamiam EndGame");
        boolean result = endGameService.lookForEndGame(event.getGame(), event.getFigure().getOpponent());
        if(!result){
            return;
        }
        endGameService.handleAfterGame(event.getGame());
        log.info("Game has ended");
    }
}
