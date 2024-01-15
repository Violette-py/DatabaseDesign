package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.dto.SaleCommodityDTO;
import com.pcs.be.entity.SaleCommodity;

import java.io.IOException;
import java.util.List;

public interface ISaleCommodityService extends IService<SaleCommodity> {

    void init() throws IOException;

    void addSaleCommodity(SaleCommodity saleCommodity);

    void updateBasicInfo(SaleCommodity saleCommodity);

    void updatePrice(Integer id, Float price, String role);

    void removeSaleCommodity(Integer id);

    SaleCommodity displayOne(Integer id);

    List<SaleCommodity> displayAll();

    List<SaleCommodity> displayAllInOneShop(String vendorName);

    List<SaleCommodity> displayAllOfOneCommodity(Integer commodityId);

    List<SaleCommodity> search(String input);
    List<SaleCommodity> searchInPlatform(String input, String platformName);

    List<SaleCommodityDTO> searchUsingTableJoin(String input);


}
