package com.chessd.chess.listener;

import com.chessd.chess.event.EndGameEvent;
import com.chessd.chess.repository.gameRepository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EndGameListener {
    private final GameDao gameDao;

    @Autowired
    public EndGameListener(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @EventListener
    public void onEndGame(EndGameEvent event){

    }
}
