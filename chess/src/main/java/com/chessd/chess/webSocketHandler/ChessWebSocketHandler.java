package com.chessd.chess.webSocketHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Handles WebSocket communication for the chess application.
 * This class extends {@link TextWebSocketHandler} to manage text-based WebSocket messages.
 * It processes messages sent by the client, delegates game logic to {@link GameHandleTextMessage},
 * and responds with appropriate messages to the client.
 */
@Component
public class ChessWebSocketHandler extends TextWebSocketHandler {
    private GameHandleTextMessage gameHandleTextMessage;

    @Autowired
    public ChessWebSocketHandler(GameHandleTextMessage gameHandleTextMessage) {
        this.gameHandleTextMessage = gameHandleTextMessage;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage(new MessageToJS("start", "game starts now", true).toJson()));
    }

    /**
     * Handles text messages received from the client.
     * Processes the message payload, updates the game state, and sends a response back to the client.
     *
     * @param session the {@link WebSocketSession} representing the connection.
     * @param message the {@link TextMessage} received from the client.
     * @throws Exception if an error occurs while processing or responding to the message.
     */
    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        // Deserialize the message into a GameHandleTextMessage object
        GameHandleTextMessage gh = objectMapper.readValue(payload, GameHandleTextMessage.class);

        // Update the game state in the handler
        gameHandleTextMessage.setGameId(gh.getGameId());
        gameHandleTextMessage.setMessage(gh.getMessage());
        gameHandleTextMessage.setMessageType(gh.getMessageType());

        // Process the message and send a response back to the client
        session.sendMessage(new TextMessage(gameHandleTextMessage.handleMessage().toJson()));

        // Log the current state of the message handler
        System.out.println(gameHandleTextMessage);
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + status.getReason());
    }
}
