package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.entity.SaleCommodity;
import com.pcs.be.entity.Vendor;
import com.pcs.be.service.ISaleCommodityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VendorControllerTests {

    @Autowired
    private VendorController vendorController;

    //    @Test
    public void displayInfo() {
        Result<Vendor> result = vendorController.displayInfo(2);
        System.out.println(result);
    }

    //    @Test
    public void displayAllInOneShop() {
        Result<List<SaleCommodity>> result = vendorController.displayAllInOneShop("华为");
        System.out.println(result);
    }

    //    @Test
    public void addSaleCommodity() {
        Date date = new Date();
        SaleCommodity saleCommodity = new SaleCommodity(1, "手机", "华为Mate60炫彩", "华为", "京东", 2050.1F, "上海", date, "华为Mate60炫彩，非你莫属");

        Result<Object> result = vendorController.addSaleCommodity(saleCommodity);
        System.out.println(result);
    }

    @Autowired
    private ISaleCommodityService saleCommodityService;

    //    @Test
    public void addSaleCommodityServiceImpl() {
        Date date = new Date();
        SaleCommodity saleCommodity = new SaleCommodity(1, "手机", "华为Mate60炫彩", "华为", "京东", 2050.1F, "上海", date, "华为Mate60炫彩，非你莫属");

        saleCommodityService.addSaleCommodity(saleCommodity);
//        System.out.println(result);
    }

    //    @Test
    public void removeSaleCommodity() {
        Result<Object> result = vendorController.removeSaleCommodity(32);
        System.out.println(result);
    }

    //            @Test
    public void updateBasicInfo() {
        Date date = new Date();
        SaleCommodity saleCommodity = new SaleCommodity(1, "手机", "华为Mate60炫彩", "华为", "京东", 2050.1F, "上海", date, "华为Mate60炫彩，非你莫属");
        saleCommodity.setId(1);

        Result<Object> result = vendorController.updateBasicInfo(saleCommodity);
        System.out.println(result);
    }

    @Test
    public void updateSaleCommodityPrice() {
<<<<<<< HEAD
//        try {
        Result<Object> result = vendorController.updateSaleCommodityPrice(60, 2522F);
        System.out.println(result);
//        } catch (Exception e) {
//            System.out.println(e.getClass().getSimpleName());
//            System.out.println(e.getMessage());
//        }
    }

    @Test
    public void updateSaleCommodityPriceService() {
//        try {
        saleCommodityService.updatePrice(60, 2222F, "vendor");
//        } catch (Exception e) {
//            System.out.println(e.getClass().getSimpleName());
//            System.out.println(e.getMessage());
//        }
=======
        try {
            Result<Object> result = vendorController.updateSaleCommodityPrice(60, 700F);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
>>>>>>> 36699da8d85a0727976a0d41fc7ede5502abe98c
    }

}
