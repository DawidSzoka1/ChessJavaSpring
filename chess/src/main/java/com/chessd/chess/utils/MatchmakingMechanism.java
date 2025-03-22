package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.User;
import com.chessd.chess.service.RandomUniqIdGenerator;
import com.chessd.chess.service.UserService;
import com.chessd.chess.service.game.GameService;
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
    private final UserService userService;
    private final RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public MatchmakingMechanism(GameService gameService,
                                UserService userService,
                                RandomUniqIdGenerator randomUniqIdGenerator) {
        this.gameService = gameService;
        this.userService = userService;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    public Map<String, String> lookForOpponent(
            WebSocketSession session,
            Queue<WebSocketSession> waitingPlayers) {
        Map<String, String> response = new HashMap<>();
        if (waitingPlayers.isEmpty()) {
            response.put("result", "no match");
            return response;
        }
        WebSocketSession player2 = waitingPlayers.poll();
        response.put("result", "success");
        response.put("player1",
                session.getPrincipal() == null ? "anonimowy1" : session.getPrincipal().getName());
        response.put("player2",
                player2.getPrincipal() == null ? "anonimowy2" : session.getPrincipal().getName());

        return response;
    }

    private void randomSideSelection(Game game, User first, User second){
        Random random = new Random();
        if(random.nextInt(0, 2) == 0){
            game.setBlack(first == null ? new User(): first);
            game.setWhite(second);
        }else{
            game.setWhite(first == null ? new User(): first);
            game.setBlack(second);
        }
    }

    public void createGame(User player1, User player2) {
        Game game = new Game(randomUniqIdGenerator.generateUniqId());
        randomSideSelection(game, player1, player2);
    }


}
