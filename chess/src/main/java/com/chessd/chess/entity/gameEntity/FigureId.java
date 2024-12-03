package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FigureId implements Serializable {

    private String gameId;
    private String customId;

    public FigureId() {}
    public FigureId(String gameId, String customId) {
        this.gameId = gameId;
        this.customId = customId;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String setCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FigureId figureId = (FigureId) o;
        return Objects.equals(gameId, figureId.gameId) && Objects.equals(customId, figureId.customId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, customId);
    }
}
