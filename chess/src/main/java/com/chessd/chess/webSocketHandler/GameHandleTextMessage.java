package com.chessd.chess.webSocketHandler;

import com.chessd.chess.service.GameService;
import com.chessd.chess.utils.Figure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Handles WebSocket text messages related to chess game operations.
 * This component processes messages received from the client, performs appropriate actions
 * (e.g., handling moves, responding to pings), and returns a response to the client.
 */
@Component
@Getter @Setter @NoArgsConstructor
public class GameHandleTextMessage {
    private String message;
    private String messageType;
    private String gameId;
    /**
     * The service used to manage chess game logic and state.
     */
    private GameService gameService;

    @Autowired
    public GameHandleTextMessage(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Processes a "move" message, validates the move, and updates the game state.
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     * @throws JsonProcessingException if serialization of the game state fails.
     */
    public MessageToJS handleMessageMove() throws JsonProcessingException {
        System.out.println("Handle message move: " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        String[] moveDetails = message.split("-");
        System.out.println("sdfg : " + Arrays.toString(moveDetails));
        Object[] valid = gameService.move(gameId, moveDetails[0], moveDetails[1], String.valueOf(message.charAt(0)));
        if ((boolean) valid[0]) {
            return new MessageToJS("MOVE", moveDetails[1], true, objectMapper.writeValueAsString(valid[1]));
        }
        return new MessageToJS("MOVE", (String) valid[1], false);
    }

    /**
     * Constructs a "pong" message response to keep the WebSocket connection alive.
     *
     * @return a {@link MessageToJS} object representing the pong response.
     */
    public MessageToJS pongMessage() {
        return new MessageToJS("PONG", "pong", true);
    }

    /**
     * Handles the received message based on its type.
     * Delegates to appropriate handlers (e.g., move handling, pong response).
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     * @throws JsonProcessingException if serialization of the game state fails.
     */
    public MessageToJS handleMessage() throws JsonProcessingException {
        return switch (messageType) {
            case "move" -> handleMessageMove();
            default -> pongMessage();
        };
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
