package com.chessd.chess.ranking.service;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.entity.RankingPosition;
import com.chessd.chess.ranking.repository.RankingPositionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RankingPositionServiceImpl implements RankingPositionService {
    private final RankingPositionDao rankingPositionDao;

    @Autowired
    public RankingPositionServiceImpl(RankingPositionDao rankingPositionDao) {
        this.rankingPositionDao = rankingPositionDao;
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
}
