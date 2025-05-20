package com.chessd.chess.ranking.repository;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.ranking.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingDao extends JpaRepository<Ranking, Integer> {
    Ranking findByName(String name);

    Ranking findByGameType(GameType gameType);
}
