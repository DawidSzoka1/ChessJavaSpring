package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.repository.GameTypeDao;
import com.chessd.chess.ranking.entity.Ranking;
import com.chessd.chess.ranking.service.RankingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameTypeServiceImpl implements GameTypeService {
    private final GameTypeDao gameTypeDao;
    private final RankingService rankingService;

    public GameTypeServiceImpl(GameTypeDao gameTypeDao, RankingService rankingService1) {
        this.gameTypeDao = gameTypeDao;
        this.rankingService = rankingService1;
    }


    @Override
    public GameType findByType(String type) {
        return gameTypeDao.findByType(type);
    }

    @Override
    public List<GameType> findAll() {
        return gameTypeDao.findAll();
    }

    @Override
    public void save(GameType gameType) {
        gameTypeDao.save(gameType);
    }

    @Override
    public void delete(GameType gameType) {
        gameTypeDao.delete(gameType);
    }

    @Override
    public Optional<GameType> findById(int id) {
        return gameTypeDao.findById(id);
    }

    @Override
    public List<GameType> findAllAvailable() {
        List<GameType> result = new ArrayList<>();
        for(GameType gameType: this.findAll()){
            Ranking existing = rankingService.findByGameType(gameType);
            if(existing == null){
                result.add(gameType);
            }
        }
        return result;
    }
}
