package com.chessd.chess.ranking.service;

import com.chessd.chess.ranking.entity.Ranking;

public interface RankingService {
    void save(Ranking ranking);

    void update(Ranking ranking);

    void delete(Ranking ranking);
}
