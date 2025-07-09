package com.chessd.chess.ranking.repository;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.entity.RankingPositionKey;
import com.chessd.chess.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RankingPositionDao extends JpaRepository<RankingPosition, RankingPositionKey> {
    Page<RankingPosition> findAllByRanking(Ranking ranking, Pageable pageable);

    List<RankingPosition> findAllByRanking(Ranking ranking);

    List<RankingPosition> findAllByUser(User user);

    List<RankingPosition> findAllByRankingAndPointsLessThan(Ranking ranking, int points);

    RankingPosition findByUserAndRanking(User user, Ranking ranking);

    List<RankingPosition> findAllByPointsAndRanking(int points, Ranking ranking);

    @Query("select position from RankingPosition where ranking=?1 and position = " +
            "(select max(position) from RankingPosition where ranking=?1)")
    int findByPositionMax(Ranking ranking);

    @Query("select position  from RankingPosition  where ranking=?1 and position = " +
            "(select max(position) from RankingPosition  where ranking=?1 and position >= ?2)")
    int findNewPosition(Ranking ranking, int newPoints);

    List<RankingPosition> findAllByRankingAndPointsBetweenOrderByPointsDesc(Ranking ranking, int pointsAfter, int pointsBefore);

    List<RankingPosition> findAllByRankingOrderByPointsDesc(Ranking ranking);
}
