package com.chessd.chess.figure.utils;

import com.chessd.chess.figure.entity.*;
import com.chessd.chess.game.entity.Game;
import com.chessd.chess.user.entity.User;
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
    public static Figure putFigure(@NotNull String figureName, String color, Position position, Game game, User owner) {
        return switch (figureName) {
            case "rook" -> new Rook(color, position, game, owner);
            case "bishop" -> new Bishop(color, position, game, owner);
            case "knight" -> new Knight(color, position, game, owner);
            case "king" -> new King(color, position, game, owner);
            case "queen" -> new Queen(color, position, game, owner);
            default -> throw new IllegalArgumentException("Unknown figure name " + figureName);
        };
    }
}
