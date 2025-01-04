package com.chessd.chess.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FigureDeserializer extends JsonDeserializer<Figure> {
    @Override
    public Figure deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if(node.isNull()){
            return null;
        }
        String type = node.get("name").asText();
        switch (type){
            case "pawn" -> new Pawn(node.get("color"). asText(), node.get("position").asText());
            case "bishop" -> new Bishop(node.get("color"). asText(), node.get("position").asText());
            case "rook" -> new Rook(node.get("color"). asText(), node.get("position").asText());
            case "knight" -> new Knight(node.get("color"). asText(), node.get("position").asText());
            case "king" -> new King(node.get("color"). asText(), node.get("position").asText());
            case "queen" -> new Queen(node.get("color"). asText(), node.get("position").asText());
            default -> throw new IllegalArgumentException("Not know name of figure " + type);
        }
        return null;
    }
}
