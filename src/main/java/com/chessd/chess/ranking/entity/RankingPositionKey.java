package com.chessd.chess.ranking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingPositionKey implements Serializable {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "ranking_id")
    private int rankingId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RankingPositionKey that = (RankingPositionKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(rankingId, that.rankingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, rankingId);
    }
}
