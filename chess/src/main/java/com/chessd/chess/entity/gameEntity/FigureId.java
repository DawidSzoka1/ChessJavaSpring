package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FigureId implements Serializable {

    private String gameId;
    private String customID;

    public FigureId() {}
    public FigureId(String gameId, String customID) {
        this.gameId = gameId;
        this.customID = customID;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String setCustomID() {
        return customID;
    }

    public void setCustomID(String customID) {
        this.customID = customID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FigureId figureId = (FigureId) o;
        return Objects.equals(gameId, figureId.gameId) && Objects.equals(customID, figureId.customID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, customID);
    }
}
