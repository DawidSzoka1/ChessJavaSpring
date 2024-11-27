package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "figure")
public class Figure {
    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="game_id", referencedColumnName = "game_id", nullable = false),
            @JoinColumn(name = "position", referencedColumnName = "position")
    })
    private Game game;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    public Figure() {}
    public Figure(Game game, String name, String position) {
        this.game = game;
        this.name = name;
        this.position = position;
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
