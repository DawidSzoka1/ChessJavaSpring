package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {
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
            this.restrictMovesInCheck(game, king);
        } else {
            game.setCheckStatus("N");
        }
        gameDao.update(game);
    }

    @Override
    public boolean isKingSafeAfterMove(Figure figure, String move, Game game) {
        Figure king = figureDao.getKing(game, figure.getColor());
        if (figure.getName().equals("king")) {
            figure = king;
        }
        HashMap<Position, Figure> board = gameDao.getBoard(game);
        Position positionRem = figure.getPosition();
        board.remove(positionRem);

        figure.makeMove(move, board);
        board.put(figure.getPosition(),figure);
        boolean check = isKingUnderAttack(king, board);
        figure.makeMove(positionRem, gameDao.getBoard(game));
        return !check;
    }

    @Override
    public void restrictMovesInCheck(Game game, Figure king) {
        List<Figure> figures = figureDao.getAllFiguresByColor(game, king.getColor());
        for (Figure figure : figures) {
            List<String> legalMoves = figure.availableMoves(gameDao.getBoard(game));
            legalMoves.removeIf(move -> !this.isKingSafeAfterMove(figure, move, game));
            figure.setMoves(legalMoves);
            figureDao.update(figure);
        }
    }

    private boolean isKingUnderAttack(Figure king, HashMap<Position, Figure> board) {
        if (king == null) return false;
        for (Figure[] row : board) {
            for (Figure f : row) {
                if (f != null && f.getColor().equals(king.getOpponent())) {
                    f.setMoves(f.availableMoves(board));
                    if (f.checkIfMoveInAvailableMoves(king.getPosition(), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
