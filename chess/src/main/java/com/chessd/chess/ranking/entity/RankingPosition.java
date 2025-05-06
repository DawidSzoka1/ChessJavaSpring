package com.chessd.chess.ranking.entity;

import com.chessd.chess.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ranking_position")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingPosition {
    @EmbeddedId
    private RankingPositionKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @MapsId("rankingId")
    @JoinColumn(name = "ranking_id")
    private Ranking ranking;

    @Column(name = "position")
    private int position;

    @Column(name = "points")
    private int points;
}
