package com.chessd.chess.web_socket.message;

import com.chessd.chess.figure.entity.Figure;
import com.chessd.chess.figure.repository.FigureDao;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.game.entity.GameType;
import com.chessd.chess.game.event.EndGameEvent;
import com.chessd.chess.game.event.ValidateMoveEvent;
import com.chessd.chess.game.service.GameTypeService;
import com.chessd.chess.game.service.RandomUniqIdGenerator;
import com.chessd.chess.game.service.GameService;
import com.chessd.chess.game.utils.GameResult;
import com.chessd.chess.user.entity.User;
import com.chessd.chess.user.service.UserService;
import com.chessd.chess.web_socket.utils.MatchmakingMechanism;
import com.chessd.chess.web_socket.utils.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * Handles WebSocket text messages related to chess game operations.
 * This component processes messages received from the client, performs appropriate actions
 * (e.g., handling moves, responding to pings), and returns a response to the client.
 */
@Slf4j
@Component
@Getter
@Setter
@NoArgsConstructor
public class CustomHandleTextMessage {
    private String message;
    private String messageType;
    private String gameId;
    private String userName;
    /**
     * The service used to manage chess game logic and state.
     */
    private GameService gameService;
    private RandomUniqIdGenerator randomUniqIdGenerator;
    private MatchmakingMechanism matchmakingMechanism;
    private UserService userService;
    private UserHelper userHelper;
    private final Map<String, Set<WebSocketSession>> sessionsByGame = new HashMap<>();
    private FigureDao figureDao;
    private GameTypeService gameTypeService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CustomHandleTextMessage(GameService gameService,
                                   RandomUniqIdGenerator randomUniqIdGenerator,
                                   MatchmakingMechanism matchmakingMechanism,
                                   UserService userService,
                                   UserHelper userHelper, FigureDao figureDao, GameTypeService gameTypeService, ApplicationEventPublisher applicationEventPublisher) {
        this.gameService = gameService;
        this.matchmakingMechanism = matchmakingMechanism;
        this.randomUniqIdGenerator = randomUniqIdGenerator;
        this.userService = userService;
        this.userHelper = userHelper;
        this.figureDao = figureDao;
        this.gameTypeService = gameTypeService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    WebSocketSession opponent(Set<WebSocketSession> playersSession, User user) {
        WebSocketSession opponent = null;
        for (WebSocketSession session : playersSession) {
            User temp = userHelper.userFromWebSession(session);
            if (!temp.getUserName().equals(user.getUserName())) {
                opponent = session;
                break;
            }
        }
        return opponent;
    }

    public MessageToJS registerSession(WebSocketSession session) {
        MessageToJS messageToJS = new MessageToJS("START", "Dodano graczy", true);
        if (!sessionsByGame.containsKey(gameId)) {
            sessionsByGame.put(gameId, new LinkedHashSet<>(List.of(session)));
            return messageToJS;
        }
        sessionsByGame.get(gameId).add(session);
        return messageToJS;
    }

    /**
     * Processes a "move" message, validates the move, and updates the game state.
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     */
    public MessageToJS handleMessageMove() throws IOException {
        System.out.println("Handle message " + messageType + ": " + message);
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new MessageToJS("ERROR", "Zaloguj sie!", false);
        }
        WebSocketSession opponentSession = this.opponent(this.sessionsByGame.get(gameId), user);
        String[] moveDetails = message.split("-");
        try {
            gameService.move(gameId,
                    moveDetails[0],
                    moveDetails[1],
                    String.valueOf(message.charAt(0)),
                    messageType.equals("take"),
                    user);
        } catch (Exception e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            return new MessageToJS("ERROR", stackTraceElement.getClassName() + " " +
                    stackTraceElement.getMethodName() + " " + stackTraceElement.getLineNumber()
                    , e.getMessage(), false);
        }
        if (opponentSession != null && opponentSession.isOpen()) {
            opponentSession.sendMessage(
                    new TextMessage(
                            new MessageToJS("ENEMY", message, true).toJson())
            );
        }
        return new MessageToJS(messageType.toUpperCase(), moveDetails[1], true);
    }

    public CustomHandleTextMessage fromPayload(String payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return objectMapper.readValue(payload, CustomHandleTextMessage.class);
    }


    /**
     * Constructs a "pong" message response to keep the WebSocket connection alive.
     *
     * @return a {@link MessageToJS} object representing the pong response.
     */
    public MessageToJS pongMessage() {
        return new MessageToJS("PONG", "pong", true);
    }


    public MessageToJS queueMessage(WebSocketSession session, Map<GameType, Queue<WebSocketSession>> waitingPlayers) throws IOException {
        GameType gameType = gameTypeService.findByType(message);
        if (gameType == null) {
            return new MessageToJS("BADREQUEST", "", false);
        }
        Queue<WebSocketSession> waitingByGameType = waitingPlayers
                .computeIfAbsent(gameType, _ -> new LinkedList<>());
        Map<String, Object> response = matchmakingMechanism.lookForOpponent(session, waitingByGameType);
        if (!(Boolean) response.get("result")) {
            waitingByGameType.add(session);
            return new MessageToJS("QUEUE", "waiting for other player..", true);
        }
        User player1 = userHelper.userFromWebSession((WebSocketSession) response.get("player1"));
        User player2 = userHelper.userFromWebSession((WebSocketSession) response.get("player2"));
        String createdId = matchmakingMechanism.createGame(player1, player2, gameType);
        ((WebSocketSession) response.get("player2"))
                .sendMessage(new TextMessage(new MessageToJS("FOUND", createdId, true).toJson()));
        return new MessageToJS("FOUND", createdId, true);
    }

    public MessageToJS cancelMessage(WebSocketSession session, Map<GameType, Queue<WebSocketSession>> waitingPlayers) {
        GameType gameType = gameTypeService.findByType(message);
        Queue<WebSocketSession> waitingByGameType = waitingPlayers
                .computeIfAbsent(gameType, _ -> new LinkedList<>());
        waitingByGameType.removeIf(player -> player.equals(session));
        return new MessageToJS("CANCEL", gameId, true);
    }


    public MessageToJS checkMoves() {
        String moveDetails = message;
        Optional<Game> temp = gameService.getGameById(this.gameId);
        if (temp.isEmpty()) {
            return new MessageToJS("MOVES", "", false);
        }
        Game game = temp.get();
        log.info(moveDetails);
        Optional<Position> tempPos = Position.fromString(moveDetails);
        if (tempPos.isEmpty()) {
            return new MessageToJS("MOVES", "", false);
        }
        Position position = tempPos.get();
        Figure current = figureDao.getFigureByPosition(
                position,
                game);
        if (current == null) {
            return new MessageToJS("MOVES", "", false);
        }
        StringBuilder moves = new StringBuilder();
        Iterator<String> iterator = current.getMoves().iterator();
        while (iterator.hasNext()) {
            moves.append(iterator.next());
            if (iterator.hasNext()) {
                moves.append(",");
            }
        }
        return new MessageToJS("MOVES", moves.toString(), true);
    }


    public void closeConnection(WebSocketSession session) {
        this.sessionsByGame.get(gameId).remove(session);
        if (this.sessionsByGame.get(gameId).isEmpty()) {
            this.sessionsByGame.remove(gameId);
        }
    }

    public MessageToJS drawHandle(MessageToJS messageToJS) throws  IOException{
        User user = userService.findByUserName(this.userName);
        WebSocketSession opponent = this.opponent(this.sessionsByGame.get(this.gameId), user);
        if(opponent != null && opponent.isOpen()){
            opponent.sendMessage(new TextMessage(
                    messageToJS.toJson()
            ));
        }
        return new MessageToJS("ERROR", "NIE MA OPPONENTA", false);
    }

    public MessageToJS sendDrawRequest() throws IOException {
       return this.drawHandle(new MessageToJS("DRAWOFFER", "Propozycja remisu", true));
    }

    public MessageToJS acceptDraw() throws IOException {
        Optional<Game> opt = gameService.getGameById(this.gameId);
        if(opt.isEmpty()){
            return new MessageToJS("ERROR", "Nie ma takiej gry", false);
        }
        applicationEventPublisher.publishEvent(new EndGameEvent(this, opt.get(), GameResult.DRAW));
        return this.drawHandle(new MessageToJS("DRAWACCEPT", "Remis zaakceptowany", true));
    }

    public MessageToJS rejectDraw() throws IOException{
        return this.drawHandle(new MessageToJS("DRAWREJECT", "Remis odrzucony", true));
    }


    public MessageToJS surrender() throws IOException {
        Optional<Game> opt = gameService.getGameById(this.gameId);
        if(opt.isEmpty()){
            return new MessageToJS("ERROR", "Nie ma takiej gry", false);
        }
        Game game = opt.get();
        User user = userService.findByUserName(this.userName);
        GameResult gameResult = game.getWhite().equals(user) ? GameResult.BLACK_WINS
                : GameResult.WHITE_WINS;
        applicationEventPublisher.publishEvent(new EndGameEvent(this, game, gameResult));
        for(WebSocketSession s: this.sessionsByGame.get(this.gameId)){
            if(s.isOpen()){
                s.sendMessage(new TextMessage(
                        new MessageToJS("SURRENDER", "Gracz: " + userName + " sie poddal" , true).toJson()
                ));
            }
        }
        return new MessageToJS("SURRENDER", "Gracz: " + userName + " sie poddal" , true);
    }
    /**
     * Handles the received message based on its type.
     * Delegates to appropriate handlers (e.g., move handling, pong response).
     *
     * @return a {@link MessageToJS} object representing the response to the client.
     */
    public MessageToJS handleMessage(WebSocketSession session) throws IOException {
        return switch (messageType) {
            case "move", "take" -> this.handleMessageMove();
            case "start" -> this.registerSession(session);
            case "getMoves" -> this.checkMoves();
            case "offer_draw" -> this.sendDrawRequest();
            case "accept_draw" -> this.acceptDraw();
            case "reject_draw" -> this.rejectDraw();
            case "surrender" -> this.surrender();
            default -> this.pongMessage();
        };
    }

    public MessageToJS handleMessage(WebSocketSession session, Map<GameType, Queue<WebSocketSession>> waitingPlayers) throws IOException {
        return switch (messageType) {
            case "queue" -> this.queueMessage(session, waitingPlayers);
            case "cancel" -> this.cancelMessage(session, waitingPlayers);
            default -> this.pongMessage();
        };
    }

    @Override
    public String toString() {
        return "message=" + message + " " + messageType;
    }
}
