package com.chessd.chess.game.repository;

import com.chessd.chess.game.entity.GameType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameTypeDao extends JpaRepository<GameType, Integer> {
    GameType findByType(String type);
}
