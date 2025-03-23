package com.chessd.chess.game.service;

import com.chessd.chess.game.repository.GameDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for generating unique game IDs.
 * Ensures that the generated ID is not already in use by checking the database.
 */
@Service
public class RandomUniqIdGenerator {
    GameDao gameDao;

    @Autowired
    public RandomUniqIdGenerator(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * Generates a unique game ID that does not conflict with existing game IDs.
     * Uses {@link UUID} to generate a random ID and ensures it is not already present in the database.
     *
     * @return A unique game ID as a {@link String}.
     */
    public String generateUniqId() {
        String uniqId = UUID.randomUUID().toString();
        while (gameDao.getGameById(uniqId).isPresent()) {
            uniqId = UUID.randomUUID().toString();
        }
        return uniqId;
    }
}
