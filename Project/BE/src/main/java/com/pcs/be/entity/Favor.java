package com.pcs.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Favor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Integer saleCommodityId;
    private Float minPrice;

    public Favor() {
        this.minPrice = 0.0F;
    }

    public Favor(Integer userId, Integer saleCommodityId) {
        this.userId = userId;
        this.saleCommodityId = saleCommodityId;
        this.minPrice = 0.0F;
    }

}
