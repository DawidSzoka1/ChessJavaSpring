package com.chessd.chess.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
