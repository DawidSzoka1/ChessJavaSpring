package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.utils.GameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean lookForEndGame(Game game, String color) {
        System.out.println("Szukam konca gry!!!!");
        boolean canAnyFigureMove = figureDao.possibleMoveByColor(game, color);
        if(canAnyFigureMove){
            return false;
        }
        System.out.println("To jest koniec gry powinno cos sie stac");
        gameService.endGame(game, GameResult.fromCheckStatus(game.getCheckStatus()));
        return true;
    }
}
