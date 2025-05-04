package com.chessd.chess.web_socket.utils;

import com.chessd.chess.game.entity.Game;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

@Component
public class MatchmakingMechanism {

    private final GameService gameService;
    private final RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public MatchmakingMechanism(GameService gameService,
                                RandomUniqIdGenerator randomUniqIdGenerator) {
        this.gameService = gameService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    public Map<String, Object> lookForOpponent(
            WebSocketSession session,
            Queue<WebSocketSession> waitingPlayers) {
        Map<String, Object> response = new HashMap<>();
        if (waitingPlayers.isEmpty()) {
            response.put("result", false);
            return response;
        }
        WebSocketSession player2 = waitingPlayers.poll();
        response.put("result", true);
        response.put("player1", session);
        response.put("player2", player2);
        return response;
    }

    private void randomSideSelection(Game game, User first, User second) {
        Random random = new Random();
        if (random.nextInt(0, 2) == 0) {
            game.setBlack(first == null ? new User() : first);
            game.setWhite(second);
        } else {
            game.setWhite(first == null ? new User() : first);
            game.setBlack(second);
        }
    }

    public String createGame(User player1, User player2) {
        Game game = new Game(randomUniqIdGenerator.generateUniqId());
        randomSideSelection(game, player1, player2);
        gameService.save(game);
        gameService.startGame(game);
        return game.getGameId();
    }

}
