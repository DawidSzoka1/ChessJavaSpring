package com.chessd.chess.utils;

import com.chessd.chess.entity.Game;
import com.chessd.chess.entity.figureEntity.*;
import org.jetbrains.annotations.NotNull;

public class CreatingFigures {
    /**
     * Creates a specific {@link Figure} based on the given figure name, color, and position.
     *
     * @param figureName The name of the figure (e.g., "rook", "knight", etc.).
     * @param color      The color of the figure ("W" for white, "B" for black).
     * @param position   The position of the figure on the chess board (e.g., "a1", "b2").
     * @return The created {@link Figure}.
     * @throws IllegalArgumentException if the figure name is invalid.
     */
    public static Figure putFigure(@NotNull String figureName, String color, String position, Game game) {
        return switch (figureName) {
            case "rook" -> new Rook(color, position, game);
            case "bishop" -> new Bishop(color, position, game);
            case "knight" -> new Knight(color, position, game);
            case "king" -> new King(color, position, game);
            case "queen" -> new Queen(color, position, game);
            default -> throw new IllegalArgumentException("Unknown figure name " + figureName);
        };
    }
}
