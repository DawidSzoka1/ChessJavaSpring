package com.chessd.chess.config;

import com.chessd.chess.webSocketHandler.ChessWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private ChessWebSocketHandler chessWebSocketHandler;

    @Autowired
    public WebSocketConfig(ChessWebSocketHandler chessWebSocketHandler){
        this.chessWebSocketHandler = chessWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chessWebSocketHandler, "/chess")
                .setAllowedOrigins("http://localhost:8080/game/classic");
    }
}
