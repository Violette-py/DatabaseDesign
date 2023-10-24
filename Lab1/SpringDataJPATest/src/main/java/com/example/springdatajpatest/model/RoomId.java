package com.example.springdatajpatest.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable  // 复合主键
public class RoomId implements Serializable {
    private Integer kdno;
    private Integer kcno;
    private Integer ccno;

    public RoomId() {
    }

    public RoomId(Integer kdno, Integer kcno, Integer ccno) {
        this.kdno = kdno;
        this.kcno = kcno;
        this.ccno = ccno;
    }
}

