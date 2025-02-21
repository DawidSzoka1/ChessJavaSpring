package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.utils.GameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EndGameServiceImpl implements EndGameService {
    private final FigureDao figureDao;
    private final GameService gameService;

    @Autowired
    public EndGameServiceImpl(FigureDao figureDao, GameService gameService) {
        this.figureDao = figureDao;
        this.gameService = gameService;
    }

    @Override
    public void lookForMate(Game game, String color) {
        boolean canAnyFigureMove = figureDao.possibleMoveByColor(game, color);
        if(canAnyFigureMove){
            return;
        }

    }

    @Override
    public void lookForStalemata(Game game, String color) {

    }
}
