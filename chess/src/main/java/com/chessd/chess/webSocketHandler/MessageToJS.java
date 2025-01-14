package com.chessd.chess.webSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a message object for communication between the backend and JavaScript (frontend).
 * This class supports serialization and deserialization of JSON messages using Jackson.
 */
public class MessageToJS {
    private String type;
    private String content;
    private boolean valid;
    private String gameBoard;

    public MessageToJS() {
    }

    public MessageToJS(String type, String content, boolean valid) {
        this.type = type;
        this.content = content;
        this.valid = valid;
    }

    public MessageToJS(String type, String content, boolean valid, String gameBoard) {
        this.type = type;
        this.content = content;
        this.valid = valid;
        this.gameBoard = gameBoard;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String gameBoard) {
        this.gameBoard = gameBoard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Converts the current object to its JSON representation.
     *
     * @return a JSON string representing the object, or an empty JSON object ({}) in case of errors.
     */
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    /**
     * Creates a {@code MessageToJS} object from its JSON representation.
     *
     * @param json the JSON string to deserialize.
     * @return a {@code MessageToJS} object.
     * @throws JsonProcessingException if deserialization fails.
     */
    public static MessageToJS fromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, MessageToJS.class);
    }
}
