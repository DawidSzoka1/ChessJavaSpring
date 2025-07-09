package com.chessd.chess.move.service;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.figure.repository.FigureDao;
import com.chessd.chess.figure.service.FigureMoveService;
import com.chessd.chess.figure.service.FigureMoveServiceFactory;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.move.entity.Move;
import com.chessd.chess.move.repository.MoveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class MoveServiceImpl implements MoveService {
    private final GameService gameService;
    private final FigureDao figureDao;
    private final FigureMoveServiceFactory figureMoveServiceFactory;
    private final MoveDao moveDao;

    @Autowired
    public MoveServiceImpl(FigureDao figureDao, FigureMoveServiceFactory figureMoveServiceFactory, GameService gameService, MoveDao moveDao) {
        this.figureDao = figureDao;
        this.figureMoveServiceFactory = figureMoveServiceFactory;
        this.gameService = gameService;
        this.moveDao = moveDao;
    }

    @Override
    public boolean isMoveValid(Figure figure, String to, Game game) throws Exception{
        if(!figure.getColor().equalsIgnoreCase(game.getNextMove())){
            throw new Exception("Not this turn");
        }
        return this.checkIfMoveInAvailableMoves(figure, to, gameService.getBoard(game));

    }
    @Override
    public boolean checkIfMoveInAvailableMoves(Figure figure, String newPosition, HashMap<Position, Figure> board){
        FigureMoveService figureMoveService = figureMoveServiceFactory.getMoveService(figure.getName());
        List<String> moves = figure.getMoves();
        if(moves == null || moves.isEmpty()){
            moves = figureMoveService.getAvailableMoves(figure, board);
        }
        return moves.contains(newPosition);
    }

    @Override
    public boolean validRowCol(int row, int col) {
        return row >= 0 && row <= 7 && col >= 0 && col <= 7;
    }

    @Override
    public void executeMove(Figure figure, String to, Game game) {
        this.makeMove(figure, to, gameService.getBoard(game));
        figureDao.update(figure);

        // Toggle the next player's turn
        game.setNextMove(game.getNextMove().equals("W") ? "B" : "W");
        gameService.update(game);
    }

    @Override
    public void handleTakingFigure(Figure figure, String to, Game game) throws Exception {
        Figure taken = figureDao.getFigureByPosition(Position.fromString(to).orElseThrow(), game);
        if (taken == null || taken.getColor().equals(figure.getColor()) || taken.getName().equals("king")) {
            throw new Exception("Cant take that figure");
        }
        figureDao.delete(taken);
    }

    @Override
    public boolean validKingMove(Figure figure, String to, Game game) throws Exception {
        Optional<Figure> check = figureDao
                .getFigureByPossibleMovesAndColor(game, figure.getOpponent(), to);
        if (check.isPresent()) {
            return false;
        }
        return this.isMoveValid(figure, to, game);
    }

    @Override
    public void updateFiguresMove(Game game) {
        FigureMoveService figureMoveService;
        HashMap<Position, Figure> board = gameService.getBoard(game);
        List<Figure> figures = figureDao.getAllFigureByGame(game);
        for(Figure figure: figures){
            figureMoveService = figureMoveServiceFactory.getMoveService(figure.getName());
            figure.setMoves(figureMoveService.getAvailableMoves(figure, board));
            figureDao.update(figure);
        }
    }

    @Override
    public void makeMove(Figure figure, String move, HashMap<Position, Figure> board) {
        FigureMoveService figureMoveService = figureMoveServiceFactory.getMoveService(figure.getName());
        figure.setPosition(Position.fromString(move).orElseThrow());
        figure.setMoves(figureMoveService.getAvailableMoves(figure, board));
    }

    @Override
    public void save(Move move) {
        moveDao.save(move);
    }

    @Override
    public Move findByMoveId(String moveId) {
        return moveDao.findByMoveId(moveId);
    }

    @Override
    public List<Move> getAllByGame(Game game) {
        return moveDao.findAllByGameId_GameId_OrderByTimeDesc(game.getGameId());
    }

}
