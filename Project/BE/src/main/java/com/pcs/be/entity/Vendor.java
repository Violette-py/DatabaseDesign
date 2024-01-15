package com.pcs.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;

    public Vendor() {
    }

    public Vendor(Integer id) {
        this.id = id;
    }

    public Vendor(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
