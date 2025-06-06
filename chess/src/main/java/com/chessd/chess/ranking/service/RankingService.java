package com.chessd.chess.ranking.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.ranking.entity.Ranking;
import java.util.List;
import java.util.Optional;


public interface RankingService {
    void save(Ranking ranking);

    void update(Ranking ranking);

    void delete(Ranking ranking);

    List<Ranking> findAll();

    Ranking findByName(String name);


    Ranking findByGameType(GameType gameType);

    Optional<Ranking> findById(int id);
}
