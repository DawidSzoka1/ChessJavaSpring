package com.chessd.chess.service;

import com.chessd.chess.entity.User;
import com.chessd.chess.entity.gameEntity.Figure;
import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.entity.gameEntity.Move;

public interface GameService {
    void startGame(Game game);
    void move(Figure figure, Game game, User user, Move move);
    void endGame(Game game);
}
