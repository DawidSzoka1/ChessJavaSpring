package com.chessd.chess.webSocketHandler;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;

public class ChessWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Game starts now"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if(payload.equals("PING")){
            session.sendMessage(new TextMessage("PONG"));
        }else{
            System.out.println(payload);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Connection closed: " + status.getReason());
    }

    @Scheduled(fixedRate= 30000)
    public void sendPing(WebSocketSession session) throws IOException {
        if(session.isOpen()){
            session.sendMessage(new TextMessage("PONG"));
        }else{
            System.out.println("Some error occurred");
        }

    }
}
