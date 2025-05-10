package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.GameType;

public interface GameTypeService {
    GameType findByType(String type);
}
