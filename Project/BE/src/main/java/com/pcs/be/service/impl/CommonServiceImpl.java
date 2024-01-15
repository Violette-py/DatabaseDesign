package com.pcs.be.service.impl;

import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;
import com.pcs.be.mapper.UserMapper;
import com.pcs.be.mapper.VendorMapper;
import com.pcs.be.service.ICommonService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public List<Integer> login(String name) throws NotFoundException {
        List<Integer> resultList = new ArrayList<>();

        User user = userMapper.findByName(name);
        if (user != null) {
            if (user.getId().equals(1)) {  // 默认管理员的id=1
                resultList.add(1);
            } else {
                resultList.add(2);
            }
            resultList.add(user.getId());
            return resultList;
        }
        Vendor vendor = vendorMapper.findByName(name);
        if (vendor != null) {
            resultList.add(3);
            resultList.add(vendor.getId());
            return resultList;
        }

        throw new NotFoundException("此用户不存在！");
    }
}
