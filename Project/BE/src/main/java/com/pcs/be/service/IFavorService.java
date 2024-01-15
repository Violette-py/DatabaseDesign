package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.dto.FavorSaleCommodityDTO;
import com.pcs.be.dto.FavorDTO;
import com.pcs.be.entity.Favor;
import com.pcs.be.entity.SaleCommodity;

import java.util.List;

public interface IFavorService extends IService<Favor> {
    void addFavor(Favor favor);
    void setMinPrice(Favor favor);
    List<FavorDTO> displayAll(Integer userId);
    FavorSaleCommodityDTO displayFavorDistributionByAgeRange(Integer ageStart, Integer ageEnd);
    List<SaleCommodity> displayTopThreeFavorSaleCommodity();
    List<String> displayTopThreeFavorPlatform();

}
