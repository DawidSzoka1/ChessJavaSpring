package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoveServiceImpl implements MoveService {
    private FigureDao figureDao;
    private GameDao gameDao;
    private CheckService checkService;

    @Autowired
    public MoveServiceImpl(FigureDao figureDao, GameDao gameDao, CheckService checkService) {
        this.figureDao = figureDao;
        this.gameDao = gameDao;
        this.checkService = checkService;
    }

    @Override
    public boolean isMoveValid(Figure figure, String to, Game game) {
        figure.setMoves(figure.availableMoves(gameDao.getBoard(game)));
        if (figure.getName().equals("king")) {
            return this.validKingMove(figure, to, game);
        }
        return figure.checkIfMoveIsValid(to, gameDao.getBoard(game));
    }

    @Override
    public void executeMove(Figure figure, String to, Game game) {
        figure.makeMove(to, gameDao.getBoard(game));
        figureDao.update(figure);

        // Toggle the next player's turn
        game.setNextMove(game.getNextMove().equals("W") ? "B" : "W");
        gameDao.update(game);
    }

    @Override
    public boolean handleTakingFigure(Figure figure, String to, Game game) {
        Figure taken = figureDao.getFigureByPosition(to, game);
        if (taken == null || taken.getColor().equals(figure.getColor()) || taken.getName().equals("king")) {
            return false;
        }
        figureDao.delete(taken);
        return true;
    }

    @Override
    public boolean validKingMove(Figure figure, String to, Game game) {
        Optional<Figure> check = figureDao
                .getFigureByPossibleMovesAndColor(game, figure.getOpponent(), to);
        if (check.isPresent()) {
            return false;
        }
        return figure.checkIfMoveIsValid(to, gameDao.getBoard(game));
    }

}
