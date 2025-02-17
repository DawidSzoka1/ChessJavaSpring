package com.chessd.chess.service;

import com.chessd.chess.entity.figureEntity.Figure;
import com.chessd.chess.entity.Game;
import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    Object[] move(String gameId, String from, String to, String color, boolean take);
    void endGame(Game game);
    Optional<Game> getGameById(String gameId);
    void save(Game game);
    void update(Game game);
    void delete(Game game);
    Figure[][] getBoard(Game game);
}
