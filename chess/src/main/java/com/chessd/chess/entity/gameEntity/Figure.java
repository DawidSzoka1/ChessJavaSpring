package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "figure")
public class Figure {

    @EmbeddedId
    private FigureId id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name="game_id", referencedColumnName = "game_id", nullable = false)
    private Game game;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "custom_id")
    private String customId;

    public Figure() {}
    public Figure(Game game, String name, String position) {
        this.id = new FigureId(game.getGameId(), "custom_id"+position);
        this.game = game;
        this.name = name;
        this.position = position;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public FigureId getId() {
        return id;
    }

    public void setId(FigureId id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "game=" + game +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
