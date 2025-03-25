package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.utils.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    Optional<Game> getGameById(String gameId);
    List<Game> getGamesByPlayerId(String playerId);
    List<Game> getAllGames();
    void save(Game game);
    void update(Game game);
    void delete(Game game);
    HashMap<Position, Figure> getBoard(Game game);
    Figure[][] getBoardAsTable(Game game);
}
