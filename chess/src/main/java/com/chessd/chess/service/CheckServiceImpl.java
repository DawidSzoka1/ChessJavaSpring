package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService{
    private FigureDao figureDao;
    private GameDao gameDao;

    @Autowired
    public CheckServiceImpl(FigureDao figureDao, GameDao gameDao) {
        this.figureDao = figureDao;
        this.gameDao = gameDao;
    }

    @Override
    public void lookForChecks(Game game) {
        Figure king = figureDao.getKing(game, game.getNextMove());
        Optional<Figure> attacker = figureDao
                .getFigureByPossibleMovesAndColor(game, king.getOpponent(), king.getPosition());

        if (attacker.isPresent()) {
            game.setCheckStatus(king.getColor());
        } else {
            game.setCheckStatus("N");
        }
        gameDao.update(game);
    }
}
