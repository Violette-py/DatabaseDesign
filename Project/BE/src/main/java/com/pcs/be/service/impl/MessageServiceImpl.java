package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.entity.Message;
import com.pcs.be.mapper.MessageMapper;
import com.pcs.be.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> displayAll(Integer userId) {
        return messageMapper.findAllByUserId(userId);
    }
}
