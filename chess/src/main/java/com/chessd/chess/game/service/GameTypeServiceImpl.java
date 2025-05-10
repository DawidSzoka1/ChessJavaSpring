package com.chessd.chess.game.service;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.repository.GameTypeDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameTypeServiceImpl implements GameTypeService {
    private final GameTypeDao gameTypeDao;

    public GameTypeServiceImpl(GameTypeDao gameTypeDao) {
        this.gameTypeDao = gameTypeDao;
    }


    @Override
    public GameType findByType(String type) {
        return gameTypeDao.findByType(type);
    }

    @Override
    public List<GameType> findAll() {
        return gameTypeDao.findAll();
    }
}
