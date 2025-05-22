package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.GameType;

import java.util.List;

public interface GameTypeService {
    GameType findByType(String type);

    List<GameType> findAll();

    void save(GameType gameType);

    void delete(GameType gameType);
}
