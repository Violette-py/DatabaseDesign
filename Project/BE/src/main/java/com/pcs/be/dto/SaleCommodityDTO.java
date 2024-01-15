package com.pcs.be.dto;

import com.pcs.be.entity.Commodity;
import com.pcs.be.entity.Platform;
import com.pcs.be.entity.Vendor;
import lombok.Data;

import java.util.Date;

/* 为返回结果重新包装的类 */
@Data
public class SaleCommodityDTO {
    private Integer id;
    private Commodity commodity;
    private Vendor vendor;
    private Platform platform;
    private String name;
    private Float price;
    private String place;
    private Date date;
    private String description;
}