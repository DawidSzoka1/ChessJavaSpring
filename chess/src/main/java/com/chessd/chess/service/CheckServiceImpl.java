package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.service.figureService.FigureMoveService;
import com.chessd.chess.service.figureService.FigureMoveServiceFactory;
import com.chessd.chess.utils.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {
    private final MoveService moveService;
    private final GameService gameService;
    private FigureDao figureDao;
    private final FigureMoveServiceFactory serviceFactory;

    @Autowired
    public CheckServiceImpl(FigureDao figureDao, FigureMoveServiceFactory serviceFactory, MoveService moveService, GameService gameService) {
        this.figureDao = figureDao;
        this.serviceFactory = serviceFactory;
        this.moveService = moveService;
        this.gameService = gameService;
    }

    @Override
    public void lookForChecks(Game game) {
        Figure king = figureDao.getKing(game, game.getNextMove());
        Optional<Figure> attacker = figureDao
                .getFigureByPossibleMovesAndColor(game, king.getOpponent(), king.getPosition().toString());
        if (attacker.isPresent()) {
            game.setCheckStatus(king.getColor());
            this.restrictMovesInCheck(game, king);
        } else {
            game.setCheckStatus("N");
        }
        gameService.update(game);
    }

    @Override
    public boolean isKingSafeAfterMove(Figure figure, String move, Game game) {
        Figure king = figureDao.getKing(game, figure.getColor());
        if (figure.getName().equals("king")) {
            figure = king;
        }
        HashMap<Position, Figure> board = gameService.getBoard(game);
        Position positionRem = figure.getPosition();
        board.remove(positionRem);
        moveService.makeMove(figure, move, board);
        board.put(figure.getPosition(), figure);
        boolean check = isKingUnderAttack(king,
                board,
                figureDao.getAllFiguresByColor(game, king.getOpponent()));
        moveService.makeMove(figure, positionRem.toString(), gameService.getBoard(game));
        return !check;
    }

    @Override
    public void restrictMovesInCheck(Game game, Figure king) {
        FigureMoveService figureMoveService;
        List<Figure> figures = figureDao.getAllFiguresByColor(game, king.getColor());
        for (Figure figure : figures) {
            figureMoveService = serviceFactory.getMoveService(figure.getName());
            List<String> legalMoves = figureMoveService.getAvailableMoves(figure, gameService.getBoard(game));
            legalMoves.removeIf(move -> !this.isKingSafeAfterMove(figure, move, game));
            figure.setMoves(legalMoves);
            figureDao.update(figure);
        }
    }

    private boolean isKingUnderAttack(Figure king, HashMap<Position, Figure> board, List<Figure> enemyFigures) {
        if (king == null) return false;
        FigureMoveService figureMoveService;
        for (Figure f : enemyFigures) {
            figureMoveService = serviceFactory.getMoveService(f.getName());
            f.setMoves(figureMoveService.getAvailableMoves(f, board));
            if (moveService.checkIfMoveInAvailableMoves(f, king.getPosition().toString(), board)) {
                return true;
            }
        }
        return false;
    }
}
