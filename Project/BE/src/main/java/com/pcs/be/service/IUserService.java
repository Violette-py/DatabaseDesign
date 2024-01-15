package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {

    User displayOne(Integer id);
    List<User> displayAll();
    void addUser(User user);
    void updateUser(User user);
    void removeUser(Integer id);

}
