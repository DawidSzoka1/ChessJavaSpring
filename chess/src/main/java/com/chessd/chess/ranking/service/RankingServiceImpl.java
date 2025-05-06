package com.chessd.chess.ranking.service;

import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.repository.RankingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RankingServiceImpl implements RankingService {
    private final RankingDao rankingDao;

    @Autowired
    public RankingServiceImpl(RankingDao rankingDao) {
        this.rankingDao = rankingDao;
    }


    @Override
    public void save(Ranking ranking) {
        rankingDao.save(ranking);
    }

    @Override
    public void update(Ranking ranking) {
        rankingDao.save(ranking);
    }

    @Override
    public void delete(Ranking ranking) {
        rankingDao.delete(ranking);
    }
}
