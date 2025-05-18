package com.chessd.chess.web_socket.handler;

import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.web_socket.message.CustomHandleTextMessage;
import com.chessd.chess.web_socket.message.MessageToJS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;

@Slf4j
@Component
public class MatchmakingHandler extends TextWebSocketHandler {

    private static Map<GameType, Queue<WebSocketSession>> waitingPlayers = new LinkedHashMap<>();
    private final CustomHandleTextMessage customHandleTextMessage;

    @Autowired
    public MatchmakingHandler(CustomHandleTextMessage customHandleTextMessage) {
        this.customHandleTextMessage = customHandleTextMessage;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage(new MessageToJS("start", "game starts now", true).toJson()));
        System.out.println("Połączono nowego gracza: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        CustomHandleTextMessage ch = customHandleTextMessage.fromPayload(message.getPayload());
        customHandleTextMessage.setGameId(ch.getGameId());
        customHandleTextMessage.setMessage(ch.getMessage());
        customHandleTextMessage.setMessageType(ch.getMessageType());

        session.sendMessage(new TextMessage(customHandleTextMessage.handleMessage(session, waitingPlayers).toJson()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        waitingPlayers.remove(session);
    }
}
