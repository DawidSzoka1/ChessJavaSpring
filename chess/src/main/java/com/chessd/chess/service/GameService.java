package com.chessd.chess.service;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.entity.Game;
import com.chessd.chess.utils.GameResult;
import com.chessd.chess.utils.Position;

import java.util.HashMap;
import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    void move(String gameId, String from, String to, String color, boolean take) throws Exception;
    void endGame(Game game, GameResult result);
    Optional<Game> getGameById(String gameId);
    void save(Game game);
    void update(Game game);
    void delete(Game game);
    HashMap<Position, Figure> getBoard(Game game);
    Figure[][] getBoardAsTable(Game game);
}
