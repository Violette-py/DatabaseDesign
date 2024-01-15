package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.Platform;
import com.pcs.be.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlatformMapper extends BaseMapper<Platform> {
    @Select("SELECT * FROM platform")
    List<Platform> findAll();

    @Select("SELECT * FROM platform WHERE id = #{id}")
    Platform findById(@Param("id") Integer id);

    @Select("SELECT * FROM platform WHERE name = #{name}")
    Platform findByName(@Param("name") String name);

    @Insert("INSERT INTO platform (name) VALUES (#{platform.name})")
    int insert(@Param("platform") Platform platform);

    @Update("UPDATE platform SET name = #{platform.name} WHERE id = #{platform.id}")
    int update(@Param("platform") Platform platform);

    @Delete("DELETE FROM platform WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);
}
