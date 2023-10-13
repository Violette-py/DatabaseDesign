package com.example.springdatajpatest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    private String registno;
    private String name;

    // 尚未添加外键关联
    private Integer kdno;
    private Integer kcno;
    private Integer ccno;
    private Integer seat;

    public Student() {
    }

    public Student(String registno, String name, Integer kdno, Integer kcno, Integer ccno, Integer seat) {
        this.registno = registno;
        this.name = name;
        this.kdno = kdno;
        this.kcno = kcno;
        this.ccno = ccno;
        this.seat = seat;
    }
}
