package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.dto.FavorDTO;
import com.pcs.be.dto.FavorSaleCommodityDTO;
import com.pcs.be.dto.SaleCommodityDTO;
import com.pcs.be.entity.*;
import com.pcs.be.service.ISaleCommodityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTests {

    @Autowired
    private UserController userController;

    //    @Test
    public void displayInfo() {
        Result<User> result = userController.displayInfo(2);
        System.out.println(result);
    }

    //    @Test
    public void displayAllSaleCommodity() {
        Result<List<SaleCommodity>> result = userController.displayAllSaleCommodity();
        for (SaleCommodity item : result.getData()) {
            System.out.println(item);
        }
    }

    //    @Test
    public void displayOneSaleCommodity() {
        Result<SaleCommodity> result = userController.displayOneSaleCommodity(1);
        System.out.println(result);
    }

    //    @Test
    public void search() {
        Result<List<SaleCommodity>> result = userController.search("苹果");
        for (SaleCommodity item : result.getData()) {
            System.out.println(item);
        }
    }

    @Autowired
    private ISaleCommodityService saleCommodityService;
//    @Test
//    public void searchUsingTableJoin() {
//        List<SaleCommodityDTO> result = saleCommodityService.searchUsingTableJoin("运动鞋");
//        for (SaleCommodityDTO item : result) {
//            System.out.println(item);
//        }
//    }

    //            @Test
    public void searchInPlatform() {
        Result<List<SaleCommodity>> result = userController.searchInPlatform("华为", "京东,天猫");
        for (SaleCommodity item : result.getData()) {
            System.out.println(item);
        }
    }

    //    @Test
    public void addFavor() {
        Favor favor = new Favor(2, 1);
        Result<Object> result = userController.addFavor(favor);
        System.out.println(result);
    }

    //    @Test
    public void setMinPrice() {
        Favor favor = new Favor(2, 1);
        favor.setId(1);
        favor.setMinPrice(101.3F);
        Result<Object> result = userController.setMinPrice(favor);
        System.out.println(result);
    }

    //                @Test
    public void displayFavor() {
        Result<List<FavorDTO>> result = userController.displayFavor(16);
        System.out.println(result);
    }

    //    @Test
    public void displayMessage() {
        Result<List<Message>> result = userController.displayMessage(2);
        System.out.println(result);
    }

    @Test
    public void displayHistoryPrice() {
        Result<List<HistoryPrice>> result = userController.displayHistoryPrice(5, "1 year");
        System.out.println(result);
    }

    @Test
    public void displayFavorDistributionByAgeRange() {
        Result<List<FavorSaleCommodityDTO>> result = userController.displayFavorDistributionByAgeRange();
        for (FavorSaleCommodityDTO item : result.getData()) {
            System.out.println(item);
        }
    }

    //        @Test
    public void displayTopThreeFavorSaleCommodity() {
        Result<List<SaleCommodity>> result = userController.displayTopThreeFavorSaleCommodity();
        for (SaleCommodity item : result.getData()) {
            System.out.println(item);
        }
    }

    //    @Test
    public void displayAllCommodity() {
        Result<List<Commodity>> result = userController.displayAllCommodity();
        for (Commodity item : result.getData()) {
            System.out.println(item);
        }
    }

    //    @Test
    public void displayAllOfOneCommodity() {
        Result<List<SaleCommodity>> result = userController.displayAllOfOneCommodity(1);
        for (SaleCommodity item : result.getData()) {
            System.out.println(item);
        }
    }

    //        @Test
    public void displayTopThreeFavorPlatform() {
        Result<List<String>> result = userController.displayTopThreeFavorPlatform();
        for (String item : result.getData()) {
            System.out.println(item);
        }
    }


}