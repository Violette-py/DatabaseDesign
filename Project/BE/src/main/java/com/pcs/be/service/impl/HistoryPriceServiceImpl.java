package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.entity.HistoryPrice;
import com.pcs.be.mapper.HistoryPriceMapper;
import com.pcs.be.service.IHistoryPriceService;
import com.pcs.be.service.ex.OperateException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HistoryPriceServiceImpl extends ServiceImpl<HistoryPriceMapper, HistoryPrice> implements IHistoryPriceService {

    private static final String HISTORY_PRICE_CSV_PATH = "src/main/resources/static/history_price.csv";

    @Autowired
    private HistoryPriceMapper historyPriceMapper;

    @Override
    public void init() throws IOException {
        Reader reader = new FileReader(HISTORY_PRICE_CSV_PATH);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean skipFirstRow = true;
        for (CSVRecord csvRecord : csvParser) {
            if (skipFirstRow) {
                skipFirstRow = false;
                continue;
            }
            Integer saleCommodityId = Integer.valueOf(csvRecord.get(0));
            Float price = Float.valueOf(csvRecord.get(1));
            String role = csvRecord.get(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime time = LocalDateTime.parse(csvRecord.get(3), formatter);
            HistoryPrice historyPrice = new HistoryPrice(saleCommodityId, price, role, time);

            try {
                addHistoryPrice(historyPrice);
            } catch (OperateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addHistoryPrice(HistoryPrice historyPrice) {
        int insertResult = historyPriceMapper.insert(historyPrice);
        if (insertResult <= 0) {
            throw new OperateException("插入历史价格信息失败！");
        }
    }

    @Override
    public List<HistoryPrice> getHistoryPrice(Integer saleCommodityId, String timeSpan) {
        return historyPriceMapper.getHistoryPrice(saleCommodityId, timeSpan);
    }
}
