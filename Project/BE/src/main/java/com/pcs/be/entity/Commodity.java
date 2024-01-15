package com.pcs.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Commodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String category;

    public Commodity() {
    }

    public Commodity(Integer id) {
        this.id = id;
    }

    public Commodity(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
