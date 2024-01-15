package com.pcs.be.dto;

import com.pcs.be.entity.SaleCommodity;
import lombok.Data;

@Data
public class FavorDTO {
    private Integer id;
    private Integer userId;
    private SaleCommodity saleCommodity;
    private Float minPrice;
}
