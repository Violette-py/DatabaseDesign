package com.example.springdatajpatest.entity;

import com.example.springdatajpatest.model.RoomId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Room {
    @EmbeddedId
    RoomId roomId;
    private String kdname;
    private String exptime;
    private String papername;

    public Room() {
    }

    public Room(Integer kdno, Integer kcno, Integer ccno, String kdname, String exptime, String papername) {
        this.roomId = new RoomId(kdno, kcno, ccno);
        this.kdname = kdname;
        this.exptime = exptime;
        this.papername = papername;
    }

}
