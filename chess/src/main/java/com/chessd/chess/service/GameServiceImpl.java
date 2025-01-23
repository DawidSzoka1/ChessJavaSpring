package com.chessd.chess.service;

import com.chessd.chess.entity.Game;
import com.chessd.chess.repository.FigureDao;
import com.chessd.chess.repository.gameRepository.GameDao;
import com.chessd.chess.repository.gameRepository.MoveDao;
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
    private final String[] figuresName = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    @Autowired
    public GameServiceImpl(GameDao gameDao, FigureDao figureDao) {
        this.gameDao = gameDao;
        this.figureDao = figureDao;
    }

    /**
     * Creates a specific {@link Figure} based on the given figure name, color, and position.
     *
     * @param figureName The name of the figure (e.g., "rook", "knight", etc.).
     * @param color      The color of the figure ("W" for white, "B" for black).
     * @param position   The position of the figure on the chess board (e.g., "a1", "b2").
     * @return The created {@link Figure}.
     * @throws IllegalArgumentException if the figure name is invalid.
     */
    public Figure putFigure(@NotNull String figureName, String color, String position, Game game) {
        return switch (figureName) {
            case "rook" -> new Rook(color, position, game);
            case "bishop" -> new Bishop(color, position, game);
            case "knight" -> new Knight(color, position, game);
            case "king" -> new King(color, position, game);
            case "queen" -> new Queen(color, position, game);
            default -> throw new IllegalArgumentException("Unknown figure name " + figureName);
        };
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
            figureDao.save(putFigure(this.figuresName[i], "W", colName + "0", game));
            figureDao.save(putFigure(this.figuresName[i], "B", colName + "7", game));
        }
    }
    public Game getGameIfExists(String gameId){
        Optional<Game> gameOpt = gameDao.getGameById(gameId);
        //TODO throw error custom
        return gameOpt.orElse(null);
    }
    /**
     * Handles the move of a figure from one position to another on the game board.
     * Verifies the move's validity, updates the game board, and returns the updated board.
     *
     * @param gameId    The ID of the game in which the move is made.
     * @param from      The starting position of the move (e.g., "a2").
     * @param to        The target position of the move (e.g., "a3").
     * @param color     The color of the player making the move.
     * @return An array where the first element indicates if the move was successful,
     * and the second element contains a success message or an error message.
     */
    @Override
    public Object[] move(String gameId, String from, String to, String color, boolean take) {
        System.out.println("Metoda move albo take");
        Object[] tab = new Object[2];
        tab[0] = false;
        Game game = getGameIfExists(gameId);
        if(game == null){
            tab[1] = "Game is null";
            return tab;
        }
        Figure[][] board = gameDao.getBoard(game);
        Figure figure = this.getFigureByPosition(from, game);
        if (figure == null) {
            tab[1] = "there inst any figure on this position";
            return tab;
        }
        figure.setMoves(figure.availableMoves(board));
        boolean valid = figure.checkIfMoveIsValid(to, board);
        if (!valid) {
            tab[1] = "Invalid move";
            return tab;
        }
        if(take){
            Figure taken = this.getFigureByPosition(to, game);
            if(taken.getName().equals("king")){
                tab[1] = "Cant take king";
                return tab;
            }
            figureDao.update(taken);
            figureDao.delete(taken);
        }
        figure.makeMove(to, board);
        figureDao.update(figure);
        tab[0] = true;
        tab[1] = gameDao.getBoard(game);
        return tab;
    }

    @Override
    public void endGame(Game game) {

    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.getGameById(gameId);
    }


    @Override
    public Figure getFigureByPosition(String position, Game game) {
        Optional<Column> c = Column.fromName(String.valueOf(position.charAt(0)));
        if(c.isEmpty()){
            System.out.println("pozycja: " + position);
            return null;
        }else {
            int col = c.get().getIndex();
            int row = Integer.parseInt(String.valueOf(position.charAt(1)));
            return this.getBoard(game)[row][col];
        }
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
