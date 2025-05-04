package com.chessd.chess.figure.entity;


import com.chessd.chess.game.entity.Game;
import com.chessd.chess.figure.utils.Position;
import com.chessd.chess.user.entity.User;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Represents a generic chess figure (piece) with common properties and behaviors.
 * This is an abstract class that must be extended by specific chess piece types.
 */
@Slf4j
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
public abstract class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color", columnDefinition = "varchar(1) check (color in ('W', 'B'))")
    private String color;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    private Game game;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "moves")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "figure_moves", joinColumns = @JoinColumn(name = "figure_id"))
    private List<String> moves;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User ownerId;

    @Column(name = "opponent", columnDefinition = "varchar(1) check(opponent in ('W', 'B'))")
    private String opponent;

    Figure(String name, String color, Position position, String imageName, Game game, User owner) {
        this.name = name;
        this.color = color;
        this.position = position;
        this.imageName = imageName;
        this.game = game;
        this.opponent = color.equals("W") ? "B" : "W";
        this.ownerId = owner;
    }

    Figure(String name, String color, int row, int col, Game game, User owner) {
        this(
                name,
                color,
                Position.fromRowCol(row, col).orElseThrow(),
                color.toUpperCase() + "_" + name + ".png",
                game,
                owner
        );
    }

    Figure(String name, String color, Position position, Game game, User owner) {
        this(name, color, position, color.toUpperCase() + "_" + name + ".png", game, owner);
    }


    @Override
    public String toString() {
        return name + '\'' +
                position + " " + id;
    }

    public int getRow() {
        return position.getRow();
    }

    public int getCol() {
        return position.getCol();
    }
}
