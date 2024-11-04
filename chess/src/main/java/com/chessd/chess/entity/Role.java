package com.chessd.chess.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;


    public Role() {
    }

    public Role(String name, int id) {
        this.name = name;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
