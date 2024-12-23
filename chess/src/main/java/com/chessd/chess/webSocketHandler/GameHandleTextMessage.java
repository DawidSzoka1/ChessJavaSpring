package com.chessd.chess.webSocketHandler;


import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GameHandleTextMessage {
    private String message;
    private String messageType;
    private Game game;
    private GameService gameService;

    public GameHandleTextMessage(){}

    @Autowired
    public GameHandleTextMessage(GameService gameService) {
        this.gameService = gameService;
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

    public void setGame(Game game) {
        this.game = game;
    }

    public GameHandleTextMessage(String message) {
        this.message = message;
    }

    public String handleMessageMove(){
        String[] moveDetails = message.split("-");
        System.out.println(game);
        return gameService.move(game, moveDetails[0].substring(1), moveDetails[1], moveDetails[0].substring(0, 1));
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

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
