package com.chessd.chess.webSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChessWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("Game starts now"));
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        GameHandleTextMessage gameHandleTextMessage =
                objectMapper.readValue(payload, GameHandleTextMessage.class);
        session.sendMessage(new TextMessage(gameHandleTextMessage.handleMessage()));
        System.out.println(gameHandleTextMessage);

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
