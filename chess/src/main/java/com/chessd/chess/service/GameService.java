package com.chessd.chess.service;

import com.chessd.chess.utils.Figure;
import com.chessd.chess.entity.Game;
import java.util.Optional;

public interface GameService {
    void startGame(Game game);
    Object[] move(String gameId, String from, String to, String color, Figure[][] lastBoard);
    void endGame(Game game);
    Optional<Game> getGameById(String gameId);
    Optional<Figure> getFigureByPosition(String position, Game game);
    void save(Game game);
    void update(Game game);
    void delete(Game game);
}
