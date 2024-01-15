package com.pcs.be.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pcs.be.entity.HistoryPrice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface HistoryPriceMapper extends BaseMapper<HistoryPrice> {

    @Insert("INSERT INTO history_price (sale_commodity_id, price, role, time) " +
            "VALUES (#{historyPrice.saleCommodityId}, #{historyPrice.price}, #{historyPrice.role}, #{historyPrice.time})")
    int insert(@Param("historyPrice") HistoryPrice historyPrice);

    @Select("SELECT h1.sale_commodity_id " +
            "FROM history_price h1 " +
            "LEFT JOIN history_price h2 ON h1.sale_commodity_id = h2.sale_commodity_id AND h1.price > h2.price " +
            "WHERE h2.price IS NULL")
    List<Integer> findLowestPriceSaleCommodities();

    // ChatGPT 你好坑！！！
    //    @Select("CALL get_history_price(#{p_sale_commodity_id, mode=IN, jdbcType=INTEGER}, #{p_time_span, mode=IN, jdbcType=VARCHAR})")
    @Select({"call pcs.get_history_price(#{p_sale_commodity_id, mode=IN, jdbcType=INTEGER}, #{p_time_span, mode=IN, jdbcType=VARCHAR})"
    })
    @Options(statementType = StatementType.CALLABLE)
    @Results({
            @Result(column = "sale_commodity_id", property = "saleCommodityId"),
            @Result(column = "time", property = "time"),
    })
    List<HistoryPrice> getHistoryPrice(@Param("p_sale_commodity_id") Integer saleCommodityId, @Param("p_time_span") String timeSpan);

}
