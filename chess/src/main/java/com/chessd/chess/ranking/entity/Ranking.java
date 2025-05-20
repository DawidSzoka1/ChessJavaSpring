package com.chessd.chess.ranking.entity;

import com.chessd.chess.game.entity.GameType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "ranking")
@AllArgsConstructor
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "game_type")
    private GameType gameType;

    @Column(name = "ranking_icon_file_name")
    private String rankingIconFileName;

    @OneToMany(mappedBy = "ranking")
    Set<RankingPosition> users;
}
