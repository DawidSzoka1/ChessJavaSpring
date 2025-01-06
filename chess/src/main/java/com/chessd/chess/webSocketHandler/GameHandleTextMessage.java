package com.chessd.chess.webSocketHandler;

import com.chessd.chess.service.GameService;
import com.chessd.chess.utils.Figure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GameHandleTextMessage {
    private String message;
    private String messageType;
    private Figure[][] board;
    private String gameId;
    private GameService gameService;

    public GameHandleTextMessage() {
    }

    @Autowired
    public GameHandleTextMessage(GameService gameService) {
        this.gameService = gameService;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Figure[][] getBoard() {
        return board;
    }

    public void setBoard(Figure[][] board) {
        this.board = board;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public MessageToJS handleMessageMove() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] moveDetails = message.substring(1).split("-");
        Object[] valid = gameService.move(gameId, moveDetails[0], moveDetails[1], String.valueOf(message.charAt(0)), board);
        if((boolean) valid[0]){
            return new MessageToJS("MOVE", moveDetails[1], true,objectMapper.writeValueAsString(valid[1]));
        }
        return new MessageToJS("MOVE", (String) valid[1], false);
    }

    public MessageToJS pongMessage() {
        return new MessageToJS("PONG", "pong", true);
    }

    public MessageToJS handleMessage() throws JsonProcessingException {
        return switch (messageType) {
            case "move" -> handleMessageMove();
            default -> pongMessage();
        };
    }

    public String getMessage() {
        return message;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
