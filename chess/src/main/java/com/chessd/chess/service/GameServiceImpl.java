package com.chessd.chess.service;

import com.chessd.chess.entity.gameEntity.Game;
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
    private MoveDao moveDao;
    private final String[] figuresName = {"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};

    @Autowired
    public GameServiceImpl(GameDao gameDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.moveDao = moveDao;
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
    public Figure putFigure(@NotNull String figureName, String color, String position) {
        return switch (figureName) {
            case "rook" -> new Rook(color, position);
            case "bishop" -> new Bishop(color, position);
            case "knight" -> new Knight(color, position);
            case "king" -> new King(color);
            case "queen" -> new Queen(color);
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
            game.placeFigure(1, i, new Pawn("W", colName + "2"));
            game.placeFigure(6, i, new Pawn("B", colName + "7"));

            //Putting other figures on a1, b1, ..., h1 and a8, b8, ..., h8
            game.placeFigure(0, i, putFigure(this.figuresName[i], "W", colName + "1"));
            game.placeFigure(7, i, putFigure(this.figuresName[i], "B", colName + "8"));
        }
    }

    /**
     * Handles the move of a figure from one position to another on the game board.
     * Verifies the move's validity, updates the game board, and returns the updated board.
     *
     * @param gameId    The ID of the game in which the move is made.
     * @param from      The starting position of the move (e.g., "a2").
     * @param to        The target position of the move (e.g., "a3").
     * @param color     The color of the player making the move.
     * @param lastBoard The board state before the move.
     * @return An array where the first element indicates if the move was successful,
     * and the second element contains a success message or an error message.
     */
    @Override
    public Object[] move(String gameId, String from, String to, String color, Figure[][] lastBoard) {
        Optional<Game> gameOpt = gameDao.getGameById(gameId);
        Object[] tab = new Object[2];
        tab[0] = false;
        if (gameOpt.isEmpty()) {
            //TODO throw error
            tab[1] = "board is empty";
            return tab;
        }
        Game game = gameOpt.get();
        game.setBoard(lastBoard);
        Figure[][] board = game.getBoard();
        int col = Column.fromName(String.valueOf(from.charAt(0))).get().getIndex();
        int row = Integer.parseInt(String.valueOf(from.charAt(1))) - 1;
        Figure figure = board[row][col];
        if (figure == null) {
            tab[1] = "there inst any figure on this position";
            return tab;
        }
        boolean valid = figure.makeMove(to, lastBoard);
        if (!valid) {
            tab[1] = "Invalid move";
            return tab;
        }
        System.out.println(figure.getPosition());
        tab[0] = true;
        tab[1] = changeBoard(figure, to, board, col, row);
        return tab;
    }

    /**
     * Updates the board after a valid move by placing the figure at its new position.
     *
     * @param figure  The figure being moved.
     * @param to      The target position for the figure.
     * @param board   The current game board.
     * @param colFrom The column index of the figure's original position.
     * @param rowFrom The row index of the figure's original position.
     * @return The updated game board.
     */
    public Figure[][] changeBoard(Figure figure, String to, Figure[][] board, int colFrom, int rowFrom) {
        int colTo = Column.fromName(String.valueOf(to.charAt(0))).get().getIndex();
        int rowTo = Integer.parseInt(String.valueOf(to.charAt(1))) - 1;
        board[rowTo][colTo] = figure;
        board[rowFrom][colFrom] = null;
        return board;
    }

    @Override
    public void endGame(Game game) {

    }

    @Override
    public Optional<Game> getGameById(String gameId) {
        return gameDao.getGameById(gameId);
    }


    @Override
    public Optional<Figure> getFigureByPosition(String position, Game game) {
        int col = Column.fromName(String.valueOf(position.charAt(0))).get().getIndex();
        int row = Integer.parseInt(String.valueOf(position.charAt(1))) - 1;
        return Optional.of(game.getBoard()[row][col]);
    }
}
