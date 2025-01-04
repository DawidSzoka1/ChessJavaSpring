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

import java.util.Arrays;


@Component
public class ChessWebSocketHandler extends TextWebSocketHandler {
    private GameHandleTextMessage gameHandleTextMessage;

    @Autowired
    public ChessWebSocketHandler(GameHandleTextMessage gameHandleTextMessage) {
        this.gameHandleTextMessage = gameHandleTextMessage;
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Game starts now"));
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        GameHandleTextMessage gh =
                objectMapper.readValue(payload, GameHandleTextMessage.class);
        if (gh.getMessageType().equals("PING")) {
            session.sendMessage(new TextMessage("PONG"));
        } else {

            gameHandleTextMessage.setBoard(gh.getBoard());
            gameHandleTextMessage.setGameId(gh.getGameId());
            gameHandleTextMessage.setMessage(gh.getMessage());
            gameHandleTextMessage.setMessageType(gh.getMessageType());
            session.sendMessage(new TextMessage(gameHandleTextMessage.handleMessage()));
            System.out.println(gameHandleTextMessage);
        }

    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + status.getReason());
    }

//    @Scheduled(fixedRate= 30000)
//    public void sendPing(WebSocketSession session) throws IOException {
//        if(session.isOpen()){
//            session.sendMessage(new TextMessage("PONG"));
//        }else{
//            System.out.println("Some error occurred");
//        }
//
//    }
}
