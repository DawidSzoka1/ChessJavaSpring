package com.chessd.chess.webSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.WebSocketMessage;

public class MessageToJS implements WebSocketMessage<MessageToJS> {
    private String type;
    private String content;
    private boolean successfulAction;

    public MessageToJS() {
    }

    public MessageToJS(String type, String content, boolean successfulAction) {
        this.type = type;
        this.content = content;
        this.successfulAction = successfulAction;
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

    public boolean isSuccessfulAction() {
        return successfulAction;
    }

    public void setSuccessfulAction(boolean successfulAction) {
        this.successfulAction = successfulAction;
    }

    public String toJson()  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        }catch (JsonProcessingException e){
            return  "{}";
        }
    }

    @Override
    public @NotNull MessageToJS getPayload() {
        return this;
    }

    @Override
    public int getPayloadLength() {
        return content != null ? content.getBytes().length : 0;
    }

    @Override
    public boolean isLast() {
        return true;
    }
}
