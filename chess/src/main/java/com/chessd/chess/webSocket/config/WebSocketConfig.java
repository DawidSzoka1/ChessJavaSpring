package com.chessd.chess.webSocket.config;

import com.chessd.chess.webSocket.handler.ChessWebSocketHandler;
import com.chessd.chess.webSocket.handler.MatchmakingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChessWebSocketHandler chessWebSocketHandler;
    private final MatchmakingHandler matchmakingHandler;

    @Autowired
    public WebSocketConfig(ChessWebSocketHandler chessWebSocketHandler, MatchmakingHandler matchmakingHandler){
        this.chessWebSocketHandler = chessWebSocketHandler;
        this.matchmakingHandler = matchmakingHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chessWebSocketHandler, "/ws/chess")
                .setAllowedOrigins("http://localhost:8080/game/classic");

        registry.addHandler(matchmakingHandler, "/ws/play")
                .setAllowedOrigins("http://localhost:8080/play");
    }
}
