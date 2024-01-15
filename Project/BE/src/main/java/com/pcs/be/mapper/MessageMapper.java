package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM message WHERE user_id = #{userId}")
    List<Message> findAllByUserId(@Param("userId") Integer userId);

}
