package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.entity.Message;

import java.util.List;

public interface IMessageService extends IService<Message> {

    List<Message> displayAll(Integer userId);

}
