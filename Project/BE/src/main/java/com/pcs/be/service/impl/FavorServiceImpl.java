package com.pcs.be.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.be.dto.FavorSaleCommodityDTO;
import com.pcs.be.dto.FavorDTO;
import com.pcs.be.entity.Favor;
import com.pcs.be.entity.SaleCommodity;
import com.pcs.be.mapper.FavorMapper;
import com.pcs.be.mapper.SaleCommodityMapper;
import com.pcs.be.service.IFavorService;
import com.pcs.be.service.ex.OperateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavorServiceImpl extends ServiceImpl<FavorMapper, Favor> implements IFavorService {

    @Autowired
    private FavorMapper favorMapper;

    @Autowired
    private SaleCommodityMapper saleCommodityMapper;

    @Override
    public List<FavorDTO> displayAll(Integer userId) {
        return favorMapper.findByUserId(userId);
    }

    @Override
    public FavorSaleCommodityDTO displayFavorDistributionByAgeRange(Integer ageStart, Integer ageEnd) {
        Integer saleCommodityId = favorMapper.findMostFavorSaleCommodityIdByAgeRange(ageStart, ageEnd); // 左闭右开
        Integer userNum = favorMapper.getFavorUserCountByAgeRangeAndCommodityId(ageStart, ageEnd, saleCommodityId);
        SaleCommodity saleCommodity = saleCommodityMapper.findById(saleCommodityId);
        return new FavorSaleCommodityDTO(ageStart, ageEnd, saleCommodity, userNum);
    }

    @Override
    public List<SaleCommodity> displayTopThreeFavorSaleCommodity() {
        List<SaleCommodity> saleCommodityList = new ArrayList<>();
        List<Integer> idList = favorMapper.findTopThreeFavorSaleCommodityIds();
        for (Integer id : idList) {
            saleCommodityList.add(saleCommodityMapper.findById(id));
        }
        return saleCommodityList;
    }

    @Override
    public List<String> displayTopThreeFavorPlatform() {
        return favorMapper.findTopThreeFavorPlatformNames();
    }

    @Override
    public void addFavor(Favor favor) {
        int insertResult = favorMapper.insert(favor);
        if (insertResult <= 0) {
            throw new OperateException("插入收藏信息失败！");
        }
    }

    @Override
    public void setMinPrice(Favor favor) {
        int updateResult = favorMapper.updateMinPrice(favor);
        if (updateResult <= 0) {
            throw new OperateException("更新收藏信息失败！");
        }

    }
}
