package com.chessd.chess.ranking.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RankingPositionService {
    void save(RankingPosition rankingPosition);

    void delete(RankingPosition rankingPosition);

    void update(RankingPosition rankingPosition);

    Page<RankingPosition> findAllByRanking(Ranking ranking, int pageNumber, int pageSize);

    void deleteAllByRanking(Ranking ranking);

    List<RankingPosition> findAllByRanking(Ranking ranking);

    List<RankingPosition> findAllByUser(User user);

    RankingPosition findByUserAndGameType(User user, GameType gameType);


    List<RankingPosition> findAllByPointsAndRanking(int points, Ranking ranking);

    int lowestPosition(Ranking ranking);

    int topPosition(List<RankingPosition> rankingPositions);

    List<RankingPosition> findAllByRankingOrderByPoints(Ranking ranking);

    void updateRanking(Ranking ranking);

    void deleteAll(User user);
}
