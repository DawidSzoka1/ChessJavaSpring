package com.chessd.chess.service;

import com.chessd.chess.entity.Game;

public interface EndGameService {
    void lookForMate(Game game, String color);
    void lookForStalemata(Game game, String color);
}
