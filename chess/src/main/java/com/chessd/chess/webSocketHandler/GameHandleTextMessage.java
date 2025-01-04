package com.chessd.chess.webSocketHandler;


import com.chessd.chess.entity.gameEntity.Game;
import com.chessd.chess.service.GameService;
import com.chessd.chess.utils.Figure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class GameHandleTextMessage {
    private String message;
    private String messageType;
    private Figure[][] board;
    private String gameId;
    private GameService gameService;

    public GameHandleTextMessage(){}

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

    public GameService getGameService() {
        return gameService;
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
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
        String[] moveDetails = message.split("-");
        System.out.println("to tutaj sie wyswietla");
        System.out.println(Arrays.deepToString(board));
        return "success";
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
