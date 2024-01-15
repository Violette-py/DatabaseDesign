package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.dto.SaleCommodityDTO;
import com.pcs.be.entity.SaleCommodity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface SaleCommodityMapper extends BaseMapper<SaleCommodity> {

    @Insert("INSERT INTO sale_commodity (commodity_id, category, name, vendor_name, platform_name, price, place, date, description) " +
            "VALUES (#{saleCommodity.commodityId},  #{saleCommodity.category}, #{saleCommodity.name}," +
            " #{saleCommodity.vendorName}, #{saleCommodity.platformName}, #{saleCommodity.price}," +
            " #{saleCommodity.place}, #{saleCommodity.date}, #{saleCommodity.description})")
    int insert(@Param("saleCommodity") SaleCommodity saleCommodity);

    @Update("UPDATE sale_commodity SET  name = #{saleCommodity.name}, place = #{saleCommodity.place}, date = #{saleCommodity.date}," +
            "description = #{saleCommodity.description} WHERE id = #{saleCommodity.id}")
    int updateBasicInfo(@Param("saleCommodity") SaleCommodity saleCommodity);

//    @Update("UPDATE sale_commodity SET price = #{price} WHERE id = #{id}")
//    int updatePrice(@Param("id") Integer id, @Param("price") Float price);

    @Insert({"call pcs.update_price(#{p_sale_commodity_id, mode=IN, jdbcType=INTEGER}, #{new_price, mode=IN, jdbcType=FLOAT}, #{role, mode=IN, jdbcType=VARCHAR})"})
    @Options(statementType = StatementType.CALLABLE, useCache = false)
        // 捕获SQL状态和信息
    void updatePrice(
            @Param("p_sale_commodity_id") int saleCommodityId,
            @Param("new_price") float newPrice,
            @Param("role") String role
    ) throws DataAccessException;  // 捕获SQL状态和信息


    @Delete("DELETE FROM sale_commodity WHERE id = #{id}")
    int deleteById(@Param("id") Integer id);

    @Select("SELECT * FROM sale_commodity")
    List<SaleCommodity> findAll();

    @Select("SELECT * FROM sale_commodity WHERE vendor_name = #{vendorName}")
    List<SaleCommodity> findAllByVendorName(@Param("vendorName") String vendorName);

    @Select("SELECT * FROM sale_commodity WHERE id = #{id}")
    SaleCommodity findById(@Param("id") Integer id);

    @Select("SELECT * FROM sale_commodity WHERE commodity_id = #{commodityId}")
    List<SaleCommodity> findByCommodityId(@Param("commodityId") Integer commodityId);

    @Select("SELECT * FROM sale_commodity sc " +
            "WHERE sc.name LIKE CONCAT('%', #{input}, '%') OR " +
            "sc.category LIKE CONCAT('%', #{input}, '%')")
    List<SaleCommodity> search(@Param("input") String input);

    @Select("SELECT * FROM sale_commodity sc " +
            "WHERE (sc.name LIKE CONCAT('%', #{input}, '%') OR " +
            "sc.category LIKE CONCAT('%', #{input}, '%')) " +
            "AND sc.platform_name = #{platformName}")
    List<SaleCommodity> searchInPlatform(@Param("input") String input, @Param("platformName") String platformName);


    // 把加载策略改成 EAGER也不行
//    @Select("SELECT sc.*, c.*, v.*, p.* FROM sale_commodity sc " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id")
//    List<SaleCommodity> findAll();

//    @Select("SELECT sc.id, sc.name, sc.price, sc.place, sc.date, sc.description, " +
//            "c.id AS commodity_id, c.name AS commodity_name, c.category AS commodity_category, " +
//            "v.id AS vendor_id, v.name AS vendor_name, v.address AS vendor_address, " +
//            "p.id AS platform_id, p.name AS platform_name " +
//            "FROM sale_commodity sc " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id")
//    @Results({  // 查询结果映射策略
//            @Result(property = "id", column = "id"),
//            @Result(property = "commodity.id", column = "commodity_id"),
//            @Result(property = "commodity.name", column = "commodity_name"),
//            @Result(property = "commodity.category", column = "commodity_category"),
//            @Result(property = "vendor.id", column = "vendor_id"),
//            @Result(property = "vendor.name", column = "vendor_name"),
//            @Result(property = "vendor.address", column = "vendor_address"),
//            @Result(property = "platform.id", column = "platform_id"),
//            @Result(property = "platform.name", column = "platform_name"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "price", column = "price"),
//            @Result(property = "place", column = "place"),
//            @Result(property = "date", column = "date"),
//            @Result(property = "description", column = "description")
//    })
//    List<SaleCommodityDTO> findAll();

//    @Select("SELECT sc.id, sc.name, sc.price, sc.place, sc.date, sc.description, " +
//            "c.id AS commodity_id, c.name AS commodity_name, c.category AS commodity_category, " +
//            "v.id AS vendor_id, v.name AS vendor_name, v.address AS vendor_address, " +
//            "p.id AS platform_id, p.name AS platform_name " +
//            "FROM sale_commodity sc " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id " +
//            "WHERE sc.id = #{id}")
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "commodity.id", column = "commodity_id"),
//            @Result(property = "commodity.name", column = "commodity_name"),
//            @Result(property = "commodity.category", column = "commodity_category"),
//            @Result(property = "vendor.id", column = "vendor_id"),
//            @Result(property = "vendor.name", column = "vendor_name"),
//            @Result(property = "vendor.address", column = "vendor_address"),
//            @Result(property = "platform.id", column = "platform_id"),
//            @Result(property = "platform.name", column = "platform_name"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "price", column = "price"),
//            @Result(property = "place", column = "place"),
//            @Result(property = "date", column = "date"),
//            @Result(property = "description", column = "description")
//    })
//    SaleCommodityDTO findByIdWithDetails(@Param("id") Integer id);

//    @Select("SELECT sc.id, sc.name, sc.price, sc.place, sc.date, sc.description, " +
//            "c.id AS commodity_id, c.name AS commodity_name, c.category AS commodity_category, " +
//            "v.id AS vendor_id, v.name AS vendor_name, v.address AS vendor_address, " +
//            "p.id AS platform_id, p.name AS platform_name " +
//            "FROM sale_commodity sc " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id " +
//            "WHERE sc.vendor_id = #{vendorId}")
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "commodity.id", column = "commodity_id"),
//            @Result(property = "commodity.name", column = "commodity_name"),
//            @Result(property = "commodity.category", column = "commodity_category"),
//            @Result(property = "vendor.id", column = "vendor_id"),
//            @Result(property = "vendor.name", column = "vendor_name"),
//            @Result(property = "vendor.address", column = "vendor_address"),
//            @Result(property = "platform.id", column = "platform_id"),
//            @Result(property = "platform.name", column = "platform_name"),
//            @Result(property = "name", column = "name"),
//            @Result(property = "price", column = "price"),
//            @Result(property = "place", column = "place"),
//            @Result(property = "date", column = "date"),
//            @Result(property = "description", column = "description")
//    })
//    List<SaleCommodityDTO> findAllByVendorId(@Param("vendorId") Integer vendorId);

//    @Select("SELECT sc.*, c.*, v.*, p.* FROM sale_commodity sc " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id " +
//            "WHERE sc.id = #{id}")
//    SaleCommodity findById(@Param("id") Integer id);

    @Select("SELECT sc.id, sc.name, sc.price, sc.place, sc.date, sc.description, " +
            "c.id AS commodity_id, c.name AS commodity_name, c.category AS commodity_category, " +
            "v.id AS vendor_id, v.name AS vendor_name, v.address AS vendor_address, " +
            "p.id AS platform_id, p.name AS platform_name " +
            "FROM sale_commodity sc " +
            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
            "LEFT JOIN platform p ON sc.platform_id = p.id " +
            "WHERE sc.name LIKE CONCAT('%', #{input}, '%') OR " +
            "c.name LIKE CONCAT('%', #{input}, '%') OR " +
            "c.category LIKE CONCAT('%', #{input}, '%')")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "commodity.id", column = "commodity_id"),
            @Result(property = "commodity.name", column = "commodity_name"),
            @Result(property = "commodity.category", column = "commodity_category"),
            @Result(property = "vendor.id", column = "vendor_id"),
            @Result(property = "vendor.name", column = "vendor_name"),
            @Result(property = "vendor.address", column = "vendor_address"),
            @Result(property = "platform.id", column = "platform_id"),
            @Result(property = "platform.name", column = "platform_name"),
            @Result(property = "name", column = "name"),
            @Result(property = "price", column = "price"),
            @Result(property = "place", column = "place"),
            @Result(property = "date", column = "date"),
            @Result(property = "description", column = "description")
    })
    List<SaleCommodityDTO> searchUsingTableJoin(@Param("input") String input);

}

