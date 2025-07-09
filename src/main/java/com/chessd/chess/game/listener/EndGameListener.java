package com.chessd.chess.game.listener;

import com.chessd.chess.game.entity.Game;
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
        Game game = event.getGame();
        if(event.getGameResult() != null){
            endGameService.handleAfterGame(game, event.getGameResult());
            return;
        }
        System.out.println("Uruchamiam EndGame");
        boolean result = endGameService.lookForEndGame(game, event.getFigure().getOpponent());
        if(!result){
            return;
        }
        endGameService.handleAfterGame(game);
        log.info("Game has ended");
    }
}
