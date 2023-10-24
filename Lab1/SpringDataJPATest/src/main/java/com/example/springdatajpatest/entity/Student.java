package com.example.springdatajpatest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Student {
    @Id
    private String registno;
    private String name;

    // 复合外键关联
    @ManyToOne
    @JoinColumn(name = "kdno", referencedColumnName = "kdno")
    @JoinColumn(name = "kcno", referencedColumnName = "kcno")
    @JoinColumn(name = "ccno", referencedColumnName = "ccno")
    private Room room;

    private Integer seat;

    public Student() {
    }

    public Student(String registno, String name, Integer kdno, Integer kcno, Integer ccno, Integer seat) {
        this.registno = registno;
        this.name = name;
        this.room = new Room(kdno, kcno, ccno);
        this.seat = seat;
    }
}
