package com.chessd.chess.webSocketHandler;

import com.chessd.chess.repository.gameRepository.GameDaoImpl;
import com.chessd.chess.repository.gameRepository.MoveDaoImpl;
import com.chessd.chess.service.GameService;
import com.chessd.chess.service.GameServiceImpl;

public class GameHandleTextMessage {
    private String message;
    private String messageType;
    private String gameId;
    public GameHandleTextMessage(){}
    public GameHandleTextMessage(String message, String gameId, String messageType){
        this.message = message;
        this.gameId = gameId;
        this.messageType = messageType;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public GameHandleTextMessage(String message) {
        this.message = message;
    }

    public String handleMessageMove(){

        return "Success";
    }
    public String pongMessage(){
        return "PONG";
    }
    public String handleMessage(){
        return switch (messageType){
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
