package com.chessd.chess.entity.gameEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "move")
public class Move {
    @Id
    @Column(name = "move_id")
    private String moveId;

}
