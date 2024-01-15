package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.entity.Commodity;
import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;
import com.pcs.be.mapper.UserMapper;
import com.pcs.be.mapper.VendorMapper;
import com.pcs.be.service.IVendorService;
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
import java.util.List;

@Service
public class VendorServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements IVendorService {
    private static final String VENDOR_CSV_PATH = "src/main/resources/static/vendor.csv";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public void init() throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(VENDOR_CSV_PATH), Charset.forName("GBK"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean skipFirstRow = true;
        for (CSVRecord csvRecord : csvParser) {
            if (skipFirstRow) {
                skipFirstRow = false;
                continue;
            }
            String name = csvRecord.get(0);
            String address = csvRecord.get(1);
            Vendor vendor = new Vendor(name, address);

            try{
                addVendor(vendor);
            } catch (ExistException e) {
                System.out.println("商家名 \"" + name + "\" 已存在，请勿重复插入！");
            }
        }
    }

    @Override
    public Vendor displayOne(Integer id) {
        return vendorMapper.findById(id);
    }

    @Override
    public List<Vendor> displayAll() {
        return vendorMapper.findAll();
    }

    @Override
    public void addVendor(Vendor vendor){
        User existingUser = userMapper.findByName(vendor.getName());
        Vendor existingVendor = vendorMapper.findByName(vendor.getName());
        if (existingUser != null || existingVendor != null) {
            throw new ExistException("此用户名已被注册过，请更换一个");
        }
        int insertResult = vendorMapper.insert(vendor);
        if (insertResult <= 0) {
            throw new OperateException("插入商家信息失败！");
        }
    }

    @Override
    public void updateVendor(Vendor vendor) {
        Vendor existingVendor = vendorMapper.findById(vendor.getId());
        if (existingVendor == null) {
            throw new NotFoundException("此商家不存在！");  // 按理来说应该不会有这种情况
        }
        if (!existingVendor.getName().equals(vendor.getName())) {  // 修改了名字，需要查重
            User sameNameUser = userMapper.findByName(vendor.getName());
            Vendor sameNameVendor = vendorMapper.findByName(vendor.getName());
            if (sameNameUser != null || sameNameVendor != null) {
                throw new ExistException("此用户名已被注册过，请更换一个！");
            }
        }
        int updateResult = vendorMapper.update(vendor);
        if (updateResult <= 0) {
            throw new OperateException("更新商家信息失败！");
        }
    }

    @Override
    public void removeVendor(Integer id) {
        Vendor existingVendor = vendorMapper.findById(id);
        if (existingVendor == null) {
            throw new NotFoundException("此商家不存在！");  // 按理来说应该不会有这种情况
        }
        int removeResult = vendorMapper.deleteById(id);
        if (removeResult <= 0) {
            throw new OperateException("删除商家失败！");
        }
    }
}
