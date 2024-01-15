package com.pcs.be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class SaleCommodity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "commodity_id", referencedColumnName = "id")
//    private Commodity commodity;
    private Integer commodityId;
    private String category;

    private String name;
    private String vendorName;
    private String platformName;
    private Float price;
    private String place;  // 产地
    private Date date;  // 生产日期
    private String description;

    public SaleCommodity() {
    }

    public SaleCommodity(Integer commodityId, String category, String name, String vendorName, String platformName, Float price, String place, Date date, String description) {
        this.commodityId = commodityId;
        this.category = category;
        this.name = name;
        this.vendorName = vendorName;
        this.platformName = platformName;
        this.price = price;
        this.place = place;
        this.date = date;
        this.description = description;
    }

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
//    private Vendor vendor;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "platform_id", referencedColumnName = "id")
//    private Platform platform;
}
