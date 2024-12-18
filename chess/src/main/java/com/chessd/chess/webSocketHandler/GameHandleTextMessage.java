package com.chessd.chess.webSocketHandler;

public class GameHandleTextMessage {
    private String message;
    private String gameId;
    public GameHandleTextMessage(String message, String gameId){
        this.message = message;
        this.gameId = gameId;
    }

    public GameHandleTextMessage(String message) {
        this.message = message;
    }

    public void handleMessageMove(){

    }

    public String getMessage() {
        return message;
    }

    public String getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "message='" + message;
    }
}
