package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.Vendor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VendorMapper extends BaseMapper<Vendor> {
    @Select("SELECT * FROM vendor")
    List<Vendor> findAll();

    @Select("SELECT * FROM vendor WHERE id = #{id}")
    Vendor findById(@Param("id") Integer id);

    @Select("SELECT * FROM vendor WHERE name = #{name}")
    Vendor findByName(@Param("name") String name);

    @Insert("INSERT INTO vendor (name, address) VALUES (#{vendor.name}, #{vendor.address})")
    int insert(@Param("vendor") Vendor vendor);

    @Update("UPDATE vendor SET name = #{vendor.name}, address = #{vendor.address} WHERE id = #{vendor.id}")
    int update(@Param("vendor") Vendor vendor);

    @Delete("DELETE FROM vendor WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}
