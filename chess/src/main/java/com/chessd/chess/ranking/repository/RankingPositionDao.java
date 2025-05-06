package com.chessd.chess.ranking.repository;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.entity.RankingPositionKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingPositionDao extends JpaRepository<RankingPosition, RankingPositionKey> {
    Page<RankingPosition> findAllByRanking(Ranking ranking, Pageable pageable);
}
