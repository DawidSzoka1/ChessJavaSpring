package com.chessd.chess.ranking.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.entity.RankingPositionKey;
import com.chessd.chess.ranking.repository.RankingPositionDao;
import com.chessd.chess.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingPositionServiceImpl implements RankingPositionService {
    private final RankingPositionDao rankingPositionDao;
    private final RankingService rankingService;

    @Autowired
    public RankingPositionServiceImpl(RankingPositionDao rankingPositionDao, RankingService rankingService) {
        this.rankingPositionDao = rankingPositionDao;
        this.rankingService = rankingService;
    }


    @Override
    public void save(RankingPosition rankingPosition) {
        rankingPositionDao.save(rankingPosition);
    }

    @Override
    public void delete(RankingPosition rankingPosition) {
        rankingPositionDao.delete(rankingPosition);
    }

    @Override
    public void update(RankingPosition rankingPosition) {
        rankingPositionDao.save(rankingPosition);
    }

    @Override
    public Page<RankingPosition> findAllByRanking(Ranking ranking, int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Order.asc("position"));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return rankingPositionDao.findAllByRanking(ranking, pageable);
    }

    @Override
    public void deletAllByRanking(Ranking ranking) {
        List<RankingPosition> positions = this.findAllByRanking(ranking);
        positions.forEach(this::delete);
    }

    @Override
    public List<RankingPosition> findAllByRanking(Ranking ranking) {
        return rankingPositionDao.findAllByRanking(ranking);
    }

    @Override
    public List<RankingPosition> findAllByUser(User user) {
        return rankingPositionDao.findAllByUser(user);
    }

    @Override
    public RankingPosition findByUserAndGameType(User user, GameType gameType) {
        Ranking ranking = rankingService.findByGameType(gameType);
        RankingPosition rankingPosition = rankingPositionDao.findByUserAndRanking(user, ranking);
        if(rankingPosition == null){
            RankingPositionKey key = new RankingPositionKey(user.getId(), ranking.getId());
            rankingPosition = new RankingPosition(key, user, ranking, this.lowestPosition(ranking), 0);
            this.save(rankingPosition);
        }
        return rankingPosition;
    }

    @Override
    public List<RankingPosition> findAllLowerThanAndRanking(int points, GameType gameType) {
        Ranking ranking = rankingService.findByGameType(gameType);
        return rankingPositionDao.findAllByRankingAndPointsLessThan(ranking, points);
    }

    @Override
    public List<RankingPosition> findAllByPointsAndRanking(int points, Ranking ranking) {
        return rankingPositionDao.findAllByPointsAndRanking(points, ranking);
    }

    @Override
    public int lowestPosition(Ranking ranking) {
        return rankingPositionDao.findByPositionMax(ranking);
    }

    @Override
    public int findNewPosition(Ranking ranking, int points) {
        RankingPosition oneAbove = rankingPositionDao
                .findTopByRankingAndPointsGreaterThanEqualOrderByPointsAscPositionAsc(ranking, points);

        return oneAbove.getPosition() + 1;
    }
}
