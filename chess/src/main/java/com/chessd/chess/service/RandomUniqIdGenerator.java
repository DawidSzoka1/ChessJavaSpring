package com.chessd.chess.service;

import com.chessd.chess.repository.gameRepository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomUniqIdGenerator {
    GameDao gameDao;

    @Autowired
    public RandomUniqIdGenerator(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public String generateUniqId() {
        String uniqId = UUID.randomUUID().toString();
        while(gameDao.getGameById(uniqId) != null) {
            uniqId = UUID.randomUUID().toString();
        }
        return uniqId;
    }
}
