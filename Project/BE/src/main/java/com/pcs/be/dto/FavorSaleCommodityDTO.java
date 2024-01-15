package com.pcs.be.dto;

import com.pcs.be.entity.SaleCommodity;
import lombok.Data;

@Data
public class FavorSaleCommodityDTO {

    // 年邻段
    private Integer ageStart;
    private Integer ageEnd;

    // 这个年龄段用户最喜爱的上架商品
    private SaleCommodity saleCommodity;

    // 这个年龄段的用户数量
    private Integer userNum;

    public FavorSaleCommodityDTO() {
    }

    public FavorSaleCommodityDTO(Integer ageStart, Integer ageEnd, SaleCommodity saleCommodity, Integer userNum) {
        this.ageStart = ageStart;
        this.ageEnd = ageEnd;
        this.saleCommodity = saleCommodity;
        this.userNum = userNum;
    }
}

