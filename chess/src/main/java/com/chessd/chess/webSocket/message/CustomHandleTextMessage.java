package com.chessd.chess.webSocket.message;

import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.utils.MatchmakingMechanism;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;

/**
 * Handles WebSocket text messages related to chess game operations.
 * This component processes messages received from the client, performs appropriate actions
 * (e.g., handling moves, responding to pings), and returns a response to the client.
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class CustomHandleTextMessage {
    private String message;
    private String messageType;
    private String gameId;
    /**
     * The service used to manage chess game logic and state.
     */
    private GameService gameService;
    private RandomUniqIdGenerator randomUniqIdGenerator;
    private MatchmakingMechanism matchmakingMechanism;

    @Autowired
    public CustomHandleTextMessage(GameService gameService,
                                   RandomUniqIdGenerator randomUniqIdGenerator,
                                   MatchmakingMechanism matchmakingMechanism) {
        this.gameService = gameService;
        this.matchmakingMechanism = matchmakingMechanism;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
    }

    /**
     * Processes a "move" message, validates the move, and updates the game state.
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     */
    public MessageToJS handleMessageMove() {
        System.out.println("Handle message " + messageType + ": " + message);
        String[] moveDetails = message.split("-");
        try {
            gameService.move(gameId,
                    moveDetails[0],
                    moveDetails[1],
                    String.valueOf(message.charAt(0)),
                    messageType.equals("take"));
        } catch (Exception e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            return new MessageToJS("ERROR", stackTraceElement.getClassName() + " " +
                    stackTraceElement.getMethodName() + " " + stackTraceElement.getLineNumber()
                    , e.getMessage(), false);
        }
        return new MessageToJS(messageType.toUpperCase(), moveDetails[1], true);
    }

    public CustomHandleTextMessage fromPayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return objectMapper.readValue(payload, CustomHandleTextMessage.class);
    }


    /**
     * Constructs a "pong" message response to keep the WebSocket connection alive.
     *
     * @return a {@link MessageToJS} object representing the pong response.
     */
    public MessageToJS pongMessage() {
        return new MessageToJS("PONG", "pong", true);
    }


    public MessageToJS queueMessage(WebSocketSession session, Queue<WebSocketSession> waitingPlayers) throws IOException {
        Map<String, String> response = matchmakingMechanism.lookForOpponent(session, waitingPlayers);
        if(response.get("result").equals("not found")){
            waitingPlayers.add(session);
            return new MessageToJS();
        }
        return new MessageToJS("QUEUE", "waiting for other player..", true);
    }

    public MessageToJS foundMessage() {
        return new MessageToJS("FOUND", "found", true);
    }

    /**
     * Handles the received message based on its type.
     * Delegates to appropriate handlers (e.g., move handling, pong response).
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     */
    public MessageToJS handleMessage() {
        return switch (messageType) {
            case "move", "take" -> handleMessageMove();
            default -> pongMessage();
        };
    }

    public MessageToJS handleMessage(WebSocketSession session, Queue<WebSocketSession> waitingPlayers) throws IOException {
        return switch (messageType) {
            case "queue" -> queueMessage(session, waitingPlayers);
            case "found" -> foundMessage();
            default -> pongMessage();
        };
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
