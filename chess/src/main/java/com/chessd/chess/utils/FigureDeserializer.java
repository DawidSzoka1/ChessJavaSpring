package com.chessd.chess.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * Custom deserializer for {@link Figure} objects.
 * This class is responsible for converting JSON data into specific {@link Figure} subclasses (Pawn, Bishop, Rook, etc.).
 * The deserialization is based on the "name" property in the JSON object, which identifies the type of chess piece.
 */
public class FigureDeserializer extends JsonDeserializer<Figure> {

    /**
     * Deserializes a JSON object into a specific {@link Figure} subclass based on the "name" property.
     * <p>
     * The JSON structure should contain at least the following fields:
     * - "name": The name/type of the chess piece (e.g., "pawn", "knight", "queen").
     * - "color": The color of the piece ("W" or "B").
     * - "position": The position of the piece on the board (e.g., "e4").
     *
     * @param jsonParser             The parser used to read the JSON content.
     * @param deserializationContext The context for the deserialization.
     * @return A specific {@link Figure} subclass (Pawn, Bishop, Rook, Knight, King, Queen) based on the "name" field.
     * @throws IOException      If an error occurs during reading the JSON content.
     * @throws JacksonException If there is a problem with the deserialization process.
     */
    @Override
    public Figure deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        // Parse the JSON tree from the parser
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        // Return null if the node is empty or null
        if (node.isNull()) {
            return null;
        }

        // Get the type of the chess piece from the "name" field
        String type = node.get("name").asText();

        // Use a switch expression to create the appropriate chess piece based on the "name"
        return switch (type) {
            case "pawn" -> new Pawn(node.get("color").asText(), node.get("position").asText());
            case "bishop" -> new Bishop(node.get("color").asText(), node.get("position").asText());
            case "rook" -> new Rook(node.get("color").asText(), node.get("position").asText());
            case "knight" -> new Knight(node.get("color").asText(), node.get("position").asText());
            case "king" -> new King(node.get("color").asText(), node.get("position").asText());
            case "queen" -> new Queen(node.get("color").asText(), node.get("position").asText());
            default -> null; // Return null if the piece name is not recognized
        };

    }
}
