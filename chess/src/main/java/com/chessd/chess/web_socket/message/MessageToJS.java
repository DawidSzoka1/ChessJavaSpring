package com.chessd.chess.web_socket.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a message object for communication between the backend and JavaScript (frontend).
 * This class supports serialization and deserialization of JSON messages using Jackson.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MessageToJS {
    private String type;
    private String content;
    private String errorMessage;
    private boolean valid;

    public MessageToJS(String type, String content,  boolean valid) {
        this.content = content;
        this.type = type;
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
