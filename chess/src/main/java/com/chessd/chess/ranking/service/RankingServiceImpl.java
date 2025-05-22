package com.chessd.chess.ranking.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.repository.RankingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public List<Ranking> findAll() {
        return rankingDao.findAll();
    }

    @Override
    public Ranking findByName(String name) {
        return rankingDao.findByName(name);
    }

    @Override
    public Ranking findByGameType(GameType gameType) {
        return rankingDao.findByGameType(gameType);
    }

    @Override
    public Optional<Ranking> findById(int id) {
        return rankingDao.findById(id);
    }
}
