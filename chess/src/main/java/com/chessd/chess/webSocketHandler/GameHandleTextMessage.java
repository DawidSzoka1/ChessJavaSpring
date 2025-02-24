package com.chessd.chess.webSocketHandler;

import com.chessd.chess.service.GameService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        }catch (Exception e){
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            return new MessageToJS("ERROR", stackTraceElement.getClassName() + " " +
                    stackTraceElement.getMethodName() + " " + stackTraceElement.getLineNumber()
                    , e.getMessage(), false);
        }
        return new MessageToJS(messageType.toUpperCase(), moveDetails[1], true);
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
     */
    public MessageToJS handleMessage()  {
        return switch (messageType) {
            case "move", "take" -> handleMessageMove();
            default -> pongMessage();
        };
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
