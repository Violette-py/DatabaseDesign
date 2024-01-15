package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.Commodity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {

    @Insert("INSERT INTO commodity (name, category) VALUES (#{commodity.name}, #{commodity.category})")
    int insert(@Param("commodity") Commodity commodity);

    @Update("UPDATE commodity SET name = #{commodity.name}, category = #{commodity.category} WHERE id = #{commodity.id}")
    int update(@Param("commodity") Commodity commodity);

    @Delete("DELETE FROM commodity WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT * FROM commodity")
    List<Commodity> findAll();

    @Select("SELECT * FROM commodity WHERE id = #{id}")
    Commodity findById(@Param("id") Integer id);

    @Select("SELECT * FROM commodity WHERE name = #{name}")
    Commodity findByName(@Param("name") String name);

    @Select("SELECT * FROM commodity WHERE name LIKE CONCAT('%', #{name}, '%')")
    List<Commodity> searchByName(@Param("name") String name);

    @Select("SELECT * FROM commodity WHERE category LIKE CONCAT('%', #{category}, '%')")
    List<Commodity> searchByCategory(@Param("category") String category);



}