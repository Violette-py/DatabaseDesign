package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.entity.HistoryPrice;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface IHistoryPriceService extends IService<HistoryPrice> {
    void init() throws IOException;

    void addHistoryPrice(HistoryPrice historyPrice);

    List<HistoryPrice> getHistoryPrice(Integer saleCommodityId, String timeSpan);
}
