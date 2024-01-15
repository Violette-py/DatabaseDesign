package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;
import com.pcs.be.mapper.UserMapper;
import com.pcs.be.mapper.VendorMapper;
import com.pcs.be.service.IUserService;
import com.pcs.be.service.ex.ExistException;
import com.pcs.be.service.ex.NotFoundException;
import com.pcs.be.service.ex.OperateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VendorMapper vendorMapper;

    @Override
    public User displayOne(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> displayAll() {
        return userMapper.findAll();
    }

    @Override
    public void addUser(User user) {
        User existingUser = userMapper.findByName(user.getName());
        Vendor existingVendor = vendorMapper.findByName(user.getName());
        if (existingUser != null || existingVendor != null) {
            throw new ExistException("此用户名已被注册过，请更换一个！");
        }
        int insertResult = userMapper.insert(user);
        if (insertResult <= 0) {
            throw new OperateException("插入用户信息失败！");
        }
    }

    @Override
    public void updateUser(User user) {
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new NotFoundException("此用户不存在！");  // 按理来说应该不会有这种情况
        }
        if (!existingUser.getName().equals(user.getName())) {  // 修改了名字，需要查重
            User sameNameUser = userMapper.findByName(user.getName());
            Vendor sameNameVendor = vendorMapper.findByName(user.getName());
            if (sameNameUser != null || sameNameVendor != null) {
                throw new ExistException("此用户名已被注册过，请更换一个！");
            }
        }
        int updateResult = userMapper.update(user);
        if (updateResult <= 0) {
            throw new OperateException("更新用户信息失败！");
        }
    }

    @Override
    public void removeUser(Integer id) {
        User existingUser = userMapper.findById(id);
        if (existingUser == null) {
            throw new NotFoundException("此用户不存在！");  // 按理来说应该不会有这种情况
        }
        int removeResult = userMapper.deleteById(id);
        if (removeResult <= 0) {
            throw new OperateException("删除用户失败！");
        }
    }

}
