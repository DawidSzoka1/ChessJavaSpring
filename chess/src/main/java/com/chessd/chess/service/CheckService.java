package com.chessd.chess.service;

import com.chessd.chess.entity.Game;

public interface CheckService {
    void lookForChecks(Game game);
}
