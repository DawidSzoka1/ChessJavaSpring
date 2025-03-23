package com.chessd.chess.webSocket.handler;

import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.webSocket.message.MessageToJS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MatchmakingHandler extends TextWebSocketHandler {

    private final Queue<WebSocketSession> waitingPlayers = new ConcurrentLinkedQueue<>();
    private final RandomUniqIdGenerator randomUniqIdGenerator;

    @Autowired
    public MatchmakingHandler(RandomUniqIdGenerator randomUniqIdGenerator) {
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage(new MessageToJS("start", "game starts now", true).toJson()));
        System.out.println("Połączono nowego gracza: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        synchronized (waitingPlayers) {
            if (waitingPlayers.isEmpty()) {
                waitingPlayers.add(session);
                session.sendMessage(new TextMessage(
                        new MessageToJS("QUEUE",
                                "waiting for other player...",
                                true).toJson()));
            } else {
                WebSocketSession player1 = waitingPlayers.poll();
                if (player1 != null) {
                    String gameId = randomUniqIdGenerator.generateUniqId();
                    player1.sendMessage(new TextMessage(
                            new MessageToJS("FOUND",
                                    gameId,
                                    true).toJson()));
                    session.sendMessage(new TextMessage(
                            new MessageToJS("FOUND",
                                    gameId,
                                    true).toJson()));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        waitingPlayers.remove(session);
    }
}
