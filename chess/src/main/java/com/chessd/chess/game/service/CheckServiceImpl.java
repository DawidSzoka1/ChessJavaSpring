package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.repository.FigureDao;
import com.chessd.chess.figure.service.FigureMoveService;
import com.chessd.chess.figure.service.FigureMoveServiceFactory;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.move.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CheckServiceImpl implements CheckService {
    private final MoveService moveService;
    private final GameService gameService;
    private final FigureDao figureDao;
    private final FigureMoveServiceFactory serviceFactory;

    @Autowired
    public CheckServiceImpl(FigureDao figureDao, FigureMoveServiceFactory serviceFactory, MoveService moveService, GameService gameService) {
        this.figureDao = figureDao;
        this.serviceFactory = serviceFactory;
        this.moveService = moveService;
        this.gameService = gameService;
    }

    @Override
    public boolean isFieldAttacked(String move, Game game, String color) {
        Optional<Figure> attacker = figureDao.getFigureByPossibleMovesAndColor(game, move, color);
        return attacker.isPresent();
    }

    @Override
    public void lookForChecks(Game game) {
        System.out.println("Szukam szacha: ");
        Figure king = figureDao.getKing(game, game.getNextMove());
        System.out.println(king.getPosition());
        Optional<Figure> attacker = figureDao
                .getFigureByPossibleMovesAndColor(game, king.getOpponent(), king.getPosition().toString());
        if (attacker.isPresent()) {
            System.out.println("mam szacha: ");
            game.setCheckStatus(king.getColor());
            this.restrictMovesInCheck(game, king);
        } else {
            game.setCheckStatus("N");
        }
        gameService.update(game);
    }

    @Override
    public boolean isKingSafeAfterMove(Figure figure, String move, Game game) {
        System.out.println("Sprawdzam krola czy bezpieczny: ");
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
