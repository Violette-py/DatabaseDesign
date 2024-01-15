package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO user (name, age, gender, phone) VALUES (#{user.name}, #{user.age}, #{user.gender}, #{user.phone})")
    int insert(@Param("user") User user);

    @Update("UPDATE user SET name = #{user.name}, age = #{user.age}, gender = #{user.gender}, phone = #{user.phone} WHERE id = #{user.id}")
    int update(@Param("user") User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}