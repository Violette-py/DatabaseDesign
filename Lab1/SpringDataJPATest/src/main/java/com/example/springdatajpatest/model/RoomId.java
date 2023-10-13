package com.example.springdatajpatest.model;

import lombok.Data;

import java.io.Serializable;

@Data
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

//    public Integer getKdno() {
//        return kdno;
//    }
//
//    public Integer getKcno() {
//        return kcno;
//    }
//
//    public Integer getCcno() {
//        return ccno;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RoomId that = (RoomId) o;
//        return Objects.equals(kdno, that.kdno) &&
//                Objects.equals(kcno, that.kcno) &&
//                Objects.equals(ccno, that.ccno);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(kdno, kcno, ccno);
//    }
}

