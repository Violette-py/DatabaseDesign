package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.entity.Commodity;
import com.pcs.be.mapper.CommodityMapper;
import com.pcs.be.service.ICommodityService;
import com.pcs.be.service.ex.ExistException;
import com.pcs.be.service.ex.NotFoundException;
import com.pcs.be.service.ex.OperateException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {
    private static final String COMMODITY_CSV_PATH = "src/main/resources/static/commodity.csv";

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public void init() throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(COMMODITY_CSV_PATH), Charset.forName("GBK"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean skipFirstRow = true;
        for (CSVRecord csvRecord : csvParser) {
            if (skipFirstRow) {
                skipFirstRow = false;
                continue;
            }
            String name = csvRecord.get(0);
            String category = csvRecord.get(1);
            Commodity commodity = new Commodity(name, category);

            try {
                addCommodity(commodity);
            } catch (ExistException e) {
                System.out.println("商品名 \"" + name + "\" 已存在，请勿重复插入！");
            }
        }
    }

    @Override
    public void addCommodity(Commodity commodity) {
        Commodity existingCommodity = commodityMapper.findByName(commodity.getName());
        if (existingCommodity != null) {
            throw new ExistException("此商品名已存在，请更换一个！");
        }
        int insertResult = commodityMapper.insert(commodity);
        if (insertResult <= 0) {
            throw new OperateException("插入商品信息失败！");
        }
    }

    @Override
    public void updateCommodity(Commodity commodity) {
        Commodity existingCommodity = commodityMapper.findById(commodity.getId());
        if (existingCommodity == null) {
            throw new NotFoundException("此商品不存在！");
        }
        if (!existingCommodity.getName().equals(commodity.getName())) {
            Commodity sameNameCommodity = commodityMapper.findByName(commodity.getName());
            if (sameNameCommodity != null) {
                throw new ExistException("此商品名已存在，请更换一个！");
            }
        }
        int updateResult = commodityMapper.update(commodity);
        if (updateResult <= 0) {
            throw new OperateException("更新商品信息失败！");
        }
    }

    @Override
    public void removeCommodity(Integer id) {
        Commodity existingCommodity = commodityMapper.findById(id);
        if (existingCommodity == null) {
            throw new NotFoundException("此商品不存在！");
        }
        int removeResult = commodityMapper.deleteById(id);
        if (removeResult <= 0) {
            throw new OperateException("删除商品失败！");
        }
    }

    @Override
    public List<Commodity> displayAll() {
        return commodityMapper.findAll();
    }

}