package com.pcs.be.service.impl;

import com.pcs.be.entity.Platform;
import com.pcs.be.entity.Vendor;
import com.pcs.be.mapper.PlatformMapper;
import com.pcs.be.service.IPlatformService;
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
public class PlatformServiceImpl implements IPlatformService {
    private static final String PLATFORM_CSV_PATH = "src/main/resources/static/platform.csv";

    @Autowired
    private PlatformMapper platformMapper;

    @Override
    public void init() throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(PLATFORM_CSV_PATH), Charset.forName("GBK"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        boolean skipFirstRow = true;
        for (CSVRecord csvRecord : csvParser) {
            if (skipFirstRow) {
                skipFirstRow = false;
                continue;
            }
            String name = csvRecord.get(0);
            Platform platform = new Platform(name);
            try{
                addPlatform(platform);
            } catch (ExistException e) {
//                System.out.println(e.getMessage());
                System.out.println("平台名 \"" + name + "\" 已存在，请勿重复插入！");
            }
        }
    }

    @Override
    public List<Platform> displayAll() {
        return platformMapper.findAll();
    }

    @Override
    public void addPlatform(Platform platform) {
        Platform existingPlatform = platformMapper.findByName(platform.getName());
        if (existingPlatform != null) {
            throw new ExistException("此平台已被注册过，请更换一个！");
        }
        int insertResult = platformMapper.insert(platform);
        if (insertResult <= 0) {
            throw new OperateException("插入平台信息失败！");
        }
    }

    @Override
    public void updatePlatform(Platform platform) {
        Platform existingPlatform = platformMapper.findById(platform.getId());
        if (existingPlatform == null) {
            throw new NotFoundException("此平台不存在！");  // 按理来说应该不会有这种情况
        }
        if (!existingPlatform.getName().equals(platform.getName())) {
            Platform sameNamePlatform = platformMapper.findByName(platform.getName());
            if (sameNamePlatform != null) {
                throw new ExistException("此平台已被注册过，请更换一个！");
            }
        }
        int updateResult = platformMapper.update(platform);
        if (updateResult <= 0) {
            throw new OperateException("更新平台信息失败！");
        }

    }

    @Override
    public void removePlatform(Integer id) {
        Platform existingPlatform = platformMapper.findById(id);
        if (existingPlatform == null) {
            throw new NotFoundException("此平台不存在！");  // 按理来说应该不会有这种情况
        }
        int removeResult = platformMapper.deleteById(id);
        if (removeResult <= 0) {
            throw new OperateException("删除平台失败！");
        }
    }
}
