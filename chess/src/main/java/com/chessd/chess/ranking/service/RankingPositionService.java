package com.chessd.chess.ranking.service;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import org.springframework.data.domain.Page;

public interface RankingPositionService {
    void save(RankingPosition rankingPosition);

    void delete(RankingPosition rankingPosition);

    void update(RankingPosition rankingPosition);

    Page<RankingPosition> findAllByRanking(Ranking ranking, int pageNumber, int pageSize);
}
