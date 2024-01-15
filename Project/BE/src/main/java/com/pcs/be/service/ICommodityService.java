package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.entity.Commodity;
import org.apache.ibatis.annotations.Param;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ICommodityService extends IService<Commodity> {
    void init() throws IOException;
    void addCommodity(Commodity commodity);
    void updateCommodity(Commodity commodity);
    void removeCommodity(Integer id);
    List<Commodity> displayAll();
}
