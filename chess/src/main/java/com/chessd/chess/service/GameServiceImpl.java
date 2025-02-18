package com.chessd.chess.service;

import com.chessd.chess.entity.figureEntity.*;
import com.chessd.chess.entity.Game;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 * Service implementation for managing chess game logic, including starting a game,
 * processing moves, and interacting with the game board.
 */
@Service
public class GameServiceImpl implements GameService {
    private GameDao gameDao;
    private FigureDao figureDao;
    private MoveService moveService;
    private CheckService checkService;
    private final String[] figuresName = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    @Autowired
    public GameServiceImpl(GameDao gameDao, FigureDao figureDao, MoveService moveService, CheckService checkService) {
        this.gameDao = gameDao;
        this.figureDao = figureDao;
        this.moveService = moveService;
        this.checkService = checkService;
    }

    /**
     * Initializes the chess board by placing figures in their starting positions.
     * This method sets up the pawns and other pieces for both white and black players.
     *
     * @param game The {@link Game} to initialize.
     */
    @Override
    public void startGame(@NotNull Game game) {
        for (int i = 0; i < 8; i++) {
            Column colName = Column.fromIndex(i).orElseThrow();

            //Putting pawns on a2, b2, ..., h2 and a7, b7, ..., h7
            figureDao.save(new Pawn("W", colName + "1", game));
            figureDao.save(new Pawn("B", colName + "6", game));

            //Putting other figures on a1, b1, ..., h1 and a8, b8, ..., h8
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "W", colName + "0", game));
            figureDao.save(CreatingFigures.putFigure(this.figuresName[i], "B", colName + "7", game));
        }
    }

    public Game getGameIfExists(String gameId) {
        Optional<Game> gameOpt = gameDao.getGameById(gameId);
        //TODO throw error custom
        return gameOpt.orElse(null);
    }

    /**
     * Handles the move of a figure from one position to another on the game board.
     * Verifies the move's validity, updates the game board, and returns the updated board.
     *
     * @param gameId The ID of the game in which the move is made.
     * @param from   The starting position of the move (e.g., "a2").
     * @param to     The target position of the move (e.g., "a3").
     * @param color  The color of the player making the move.
     * @return An array where the first element indicates if the move was successful,
     * and the second element contains a success message or an error message.
     */
    @Override
    public Object[] move(String gameId, String from, String to, String color, boolean take) {

        Game game = this.getGameIfExists(gameId);
        if (game == null) return createResponse(false, "Game is null");

        Figure figure = figureDao.getFigureByPosition(from, game);
        if (figure == null) return createResponse(false, "There is no figure on this position");

        if (!moveService.isMoveValid(figure, to, game)) return createResponse(false, "Invalid move");

        if (take && !moveService.handleTakingFigure(figure, to, game))
            return createResponse(false, "Invalid take operation");
        if (!game.getCheckStatus().equals("N")) {
            return this.processCheckMove(game, figure, to);
        }

        this.afterSuccessMove(game, figure, to);
        return createResponse(true, "Valid move");
    }

    private Object[] processCheckMove(Game game, Figure figure, String to) {
        if(!checkService.isKingSafeAfterMove(figure, to, game)){
            return createResponse(false, "Doesnt escape check");
        }
        this.afterSuccessMove(game, figure, to);
        return createResponse(true, "Valid move");
    }

    private void afterSuccessMove(Game game, Figure figure, String to) {
        moveService.executeMove(figure, to, game);
        checkService.lookForChecks(game);
    }

    public Object[] createResponse(boolean result, String message) {
        return new Object[]{result, message};
    }

    @Override
    public void endGame(Game game) {

    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.getGameById(gameId);
    }

    @Override
    public void save(Game game) {
        gameDao.save(game);
    }

    @Override
    public void update(Game game) {
        gameDao.update(game);
    }

    @Override
    public void delete(Game game) {
        gameDao.delete(game);
    }

    @Override
    public Figure[][] getBoard(Game game) {
        return gameDao.getBoard(game);
    }
}
