package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.dto.FavorDTO;
import com.pcs.be.entity.Favor;
import com.pcs.be.entity.SaleCommodity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavorMapper extends BaseMapper<Favor> {

    @Insert("INSERT INTO favor (user_id, sale_commodity_id, min_price) VALUES (#{favor.userId}, #{favor.saleCommodityId}, #{favor.minPrice})")
    int insert(@Param("favor") Favor favor);

    @Update("UPDATE favor SET min_price = #{favor.minPrice} WHERE id = #{favor.id}")
    int updateMinPrice(@Param("favor") Favor favor);

    @Select("SELECT f.sale_commodity_id " +
            "FROM favor f " +
            "JOIN user u ON f.user_id = u.id " +
            "WHERE u.age >= #{ageStart} AND u.age < #{ageEnd} " +
            "GROUP BY f.sale_commodity_id " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1")
    Integer findMostFavorSaleCommodityIdByAgeRange(@Param("ageStart") Integer ageStart, @Param("ageEnd") Integer ageEnd);

    @Select("SELECT COUNT(*) " +
            "FROM favor f " +
            "JOIN user u ON f.user_id = u.id " +
            "WHERE u.age >= #{ageStart} AND u.age < #{ageEnd} " +
            "AND f.sale_commodity_id = #{saleCommodityId}")
    Integer getFavorUserCountByAgeRangeAndCommodityId(@Param("ageStart") Integer ageStart,
                                                      @Param("ageEnd") Integer ageEnd,
                                                      @Param("saleCommodityId") Integer saleCommodityId);

    @Select("SELECT sale_commodity_id FROM favor " +
            "GROUP BY sale_commodity_id " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 3")
    List<Integer> findTopThreeFavorSaleCommodityIds();

    @Select("SELECT sc.platform_name " +
            "FROM favor f " +
            "JOIN sale_commodity sc ON f.sale_commodity_id = sc.id " +
            "GROUP BY platform_name " +
            "ORDER BY COUNT(*) DESC  " +
            "LIMIT 3")
    List<String> findTopThreeFavorPlatformNames();

    @Results(id = "favorDTOResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "minPrice", column = "min_price"),
            @Result(property = "saleCommodity", column = "saleCommodity_id",
                    javaType = SaleCommodity.class, one = @One(select = "getSaleCommodity"))
    })
    @Select("SELECT f.id AS id, f.user_id AS user_id, f.min_price AS min_price, " +
            "sc.id AS saleCommodity_id " +
            "FROM favor f " +
            "JOIN sale_commodity sc ON f.sale_commodity_id = sc.id " +
            "WHERE f.user_id = #{userId}")
    List<FavorDTO> findByUserId(@Param("userId") Integer userId);

    @Results(id = "saleCommodityResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "commodityId", column = "commodity_id"),
            @Result(property = "category", column = "category"),
            @Result(property = "name", column = "name"),
            @Result(property = "vendorName", column = "vendor_name"),
            @Result(property = "platformName", column = "platform_name"),
            @Result(property = "price", column = "price"),
            @Result(property = "place", column = "place"),
            @Result(property = "date", column = "date"),
            @Result(property = "description", column = "description")
    })
    @Select("SELECT * FROM sale_commodity WHERE id = #{id}")
    SaleCommodity getSaleCommodity(@Param("id") Integer saleCommodityId);


//    @Select("SELECT f.id AS id, f.user_id AS userId, f.min_price AS minPrice, " +
//            "sc.id AS saleCommodity_id, sc.commodity_id AS saleCommodity_commodityId, " +
//            "sc.category AS saleCommodity_category, sc.name AS saleCommodity_name, " +
//            "sc.vendor_name AS saleCommodity_vendorName, sc.platform_name AS saleCommodity_platformName, " +
//            "sc.price AS saleCommodity_price, sc.place AS saleCommodity_place, " +
//            "sc.date AS saleCommodity_date, sc.description AS saleCommodity_description " +
//            "FROM favor f " +
//            "JOIN sale_commodity sc ON f.sale_commodity_id = sc.id " +
//            "WHERE f.user_id = #{userId}")
//    List<FavorDTO> findByUserId(@Param("userId") Integer userId);

//    @Select("SELECT f.id, f.user_id AS userId, f.min_price AS minPrice, " +
//            "sc.id AS saleCommodityDTO_id, sc.name AS saleCommodityDTO_name, sc.price AS saleCommodityDTO_price, " +
//            "sc.place AS saleCommodityDTO_place, sc.date AS saleCommodityDTO_date, sc.description AS saleCommodityDTO_description, " +
//            "c.id AS commodity_id, c.name AS commodity_name, c.category AS commodity_category, " +
//            "v.id AS vendor_id, v.name AS vendor_name, v.address AS vendor_address, " +
//            "p.id AS platform_id, p.name AS platform_name " +
//            "FROM favor f " +
//            "LEFT JOIN sale_commodity sc ON f.sale_commodity_id = sc.id " +
//            "LEFT JOIN commodity c ON sc.commodity_id = c.id " +
//            "LEFT JOIN vendor v ON sc.vendor_id = v.id " +
//            "LEFT JOIN platform p ON sc.platform_id = p.id " +
//            "WHERE f.user_id = #{userId}")
//    @Results({
//            @Result(property = "id", column = "id"),
//            @Result(property = "userId", column = "userId"),
//            @Result(property = "minPrice", column = "minPrice"),
//            @Result(property = "saleCommodityDTO.id", column = "saleCommodityDTO_id"),
//            @Result(property = "saleCommodityDTO.name", column = "saleCommodityDTO_name"),
//            @Result(property = "saleCommodityDTO.price", column = "saleCommodityDTO_price"),
//            @Result(property = "saleCommodityDTO.place", column = "saleCommodityDTO_place"),
//            @Result(property = "saleCommodityDTO.date", column = "saleCommodityDTO_date"),
//            @Result(property = "saleCommodityDTO.description", column = "saleCommodityDTO_description"),
//            @Result(property = "saleCommodityDTO.commodity.id", column = "commodity_id"),
//            @Result(property = "saleCommodityDTO.commodity.name", column = "commodity_name"),
//            @Result(property = "saleCommodityDTO.commodity.category", column = "commodity_category"),
//            @Result(property = "saleCommodityDTO.vendor.id", column = "vendor_id"),
//            @Result(property = "saleCommodityDTO.vendor.name", column = "vendor_name"),
//            @Result(property = "saleCommodityDTO.vendor.address", column = "vendor_address"),
//            @Result(property = "saleCommodityDTO.platform.id", column = "platform_id"),
//            @Result(property = "saleCommodityDTO.platform.name", column = "platform_name")
//    })
//    List<FavorDTO> findByUserId(@Param("userId") Integer userId);

}
