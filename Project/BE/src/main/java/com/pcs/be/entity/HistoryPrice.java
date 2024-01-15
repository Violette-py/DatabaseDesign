package com.pcs.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class HistoryPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer saleCommodityId;
    private Float price;
    private String role;
    private LocalDateTime time;

    public HistoryPrice() {
    }

    public HistoryPrice(Integer saleCommodityId, Float price, String role) {
        this.saleCommodityId = saleCommodityId;
        this.price = price;
        this.role = role;
        this.time = LocalDateTime.now();
    }

    public HistoryPrice(Integer saleCommodityId, Float price, String role, LocalDateTime time) {
        this.saleCommodityId = saleCommodityId;
        this.price = price;
        this.role = role;
        this.time = time;
    }

}
