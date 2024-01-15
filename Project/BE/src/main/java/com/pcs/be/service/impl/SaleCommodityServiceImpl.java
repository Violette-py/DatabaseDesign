package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.dto.SaleCommodityDTO;
import com.pcs.be.entity.Commodity;
import com.pcs.be.entity.SaleCommodity;
import com.pcs.be.mapper.SaleCommodityMapper;
import com.pcs.be.service.ISaleCommodityService;
import com.pcs.be.service.ex.ExistException;
import com.pcs.be.service.ex.LimitException;
import com.pcs.be.service.ex.NotFoundException;
import com.pcs.be.service.ex.OperateException;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaleCommodityServiceImpl extends ServiceImpl<SaleCommodityMapper, SaleCommodity> implements ISaleCommodityService {
    private static final String SALE_COMMODITY_CSV_PATH = "src/main/resources/static/sale_commodity.csv";
    @Autowired
    private SaleCommodityMapper saleCommodityMapper;

    @Override
    public void init() throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(SALE_COMMODITY_CSV_PATH), Charset.forName("GBK"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean skipFirstRow = true;
        for (CSVRecord csvRecord : csvParser) {
            if (skipFirstRow) {
                skipFirstRow = false;
                continue;
            }
            Integer commodity_id = Integer.valueOf(csvRecord.get(0));
            String category = csvRecord.get(1);
            String name = csvRecord.get(2);
            String vendorName = csvRecord.get(3);
            String platformName = csvRecord.get(4);
            Float price = Float.valueOf(csvRecord.get(5));
            String place = csvRecord.get(6);
            Date date = convertStringToDate(csvRecord.get(7));
            String description = csvRecord.get(8);

            SaleCommodity saleCommodity = new SaleCommodity(commodity_id, category, name, vendorName, platformName, price, place, date, description);
            addSaleCommodity(saleCommodity);
        }
    }

    @Override
    @Transactional
    public void addSaleCommodity(SaleCommodity saleCommodity) {
        int insertResult = saleCommodityMapper.insert(saleCommodity);
        if (insertResult <= 0) {
            throw new OperateException("插入上架商品信息失败！");
        }
    }

    @Override
//    @Transactional
    public void updateBasicInfo(SaleCommodity saleCommodity) {
        SaleCommodity existingSaleCommodity = saleCommodityMapper.findById(saleCommodity.getId());
        if (existingSaleCommodity == null) {
            throw new NotFoundException("此上架商品不存在！");
        }
        int updateResult = saleCommodityMapper.updateBasicInfo(saleCommodity);
        if (updateResult <= 0) {
            throw new OperateException("更新上架商品基础信息失败！");
        }
    }

    @Override
//    @Transactional
    public void updatePrice(Integer id, Float price, String role) {
        SaleCommodity existingSaleCommodity = saleCommodityMapper.findById(id);
        if (existingSaleCommodity == null) {
            throw new NotFoundException("此上架商品不存在！");
        }
        try {
            saleCommodityMapper.updatePrice(id, price, role);
        } catch (DataAccessException e) {
            throw new LimitException("商家不能在一天内多次修改同一上架商品的价格！");
        }
    }

    @Override
    @Transactional
    public void removeSaleCommodity(Integer id) {
        SaleCommodity existingSaleCommodity = saleCommodityMapper.findById(id);
        if (existingSaleCommodity == null) {
            throw new NotFoundException("此上架商品不存在！");
        }
        int removeResult = saleCommodityMapper.deleteById(id);
        if (removeResult <= 0) {
            throw new OperateException("删除上架商品失败！");
        }
    }

    @Override
    public SaleCommodity displayOne(Integer id) {
        return saleCommodityMapper.findById(id);
    }

    @Override
    public List<SaleCommodity> displayAll() {
        return saleCommodityMapper.findAll();
    }

    @Override
    public List<SaleCommodity> displayAllInOneShop(String vendorName) {
        return saleCommodityMapper.findAllByVendorName(vendorName);
    }

    @Override
    public List<SaleCommodity> search(String input) {
        return saleCommodityMapper.search(input);
    }

    @Override
    public List<SaleCommodity> searchInPlatform(String input, String platformName) {
        return saleCommodityMapper.searchInPlatform(input, platformName);
    }

    @Override
    public List<SaleCommodityDTO> searchUsingTableJoin(String input) {
        return saleCommodityMapper.searchUsingTableJoin(input);
    }

    @Override
    public List<SaleCommodity> displayAllOfOneCommodity(Integer commodityId) {
        return saleCommodityMapper.findByCommodityId(commodityId);
    }

    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

