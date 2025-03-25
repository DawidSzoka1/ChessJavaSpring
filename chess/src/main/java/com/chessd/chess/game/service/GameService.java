package com.chessd.chess.game.service;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.figure.utils.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    void move(String gameId, String from, String to, String color, boolean take) throws Exception;
    void endGame(Game game, GameResult result);
    List<Game> getGamesByPlayerUsername(String username);
    Optional<Game> getGameById(String gameId);
    void save(Game game);
    void update(Game game);
    void delete(Game game);
    HashMap<Position, Figure> getBoard(Game game);
    Figure[][] getBoardAsTable(Game game);
}
