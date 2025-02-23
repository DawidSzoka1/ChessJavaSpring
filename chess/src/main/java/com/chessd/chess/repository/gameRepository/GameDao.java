package com.chessd.chess.repository.gameRepository;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.utils.Position;

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
