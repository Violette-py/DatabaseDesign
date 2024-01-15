package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.entity.Commodity;
import com.pcs.be.entity.Platform;
import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminControllerTests {

    @Autowired
    private AdminController adminController;

    /* ---------------------------- 初始化系统 ---------------------------- */

        @Test
    public void init() {
            adminController.initSaleCommodity();
//        adminController.init();
    }

    /* ---------------------------- 管理用户 ---------------------------- */

    //    @Test
    public void displayAllUser() {
        Result<List<User>> result = adminController.displayAllUser();
        System.out.println(result);
    }

    //        @Test
    public void addUser() {
        try {
            User user = new User();
            // user.setId(1);
            user.setName("Hello");
            user.setAge(21);
            user.setGender("Female");
            user.setPhone("10086");

            adminController.addUser(user);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //    @Test
    public void updateUser() {
        try {
            User user = new User();

            user.setId(1);
            user.setName("Rosy");  // 用户名重复
            user.setAge(21);
            user.setGender("Female");
            user.setPhone("10086");

            adminController.updateUser(user);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //        @Test
    public void removeUser() {
        try {
            adminController.removeUser(6);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    /* ---------------------------- 管理商家 ---------------------------- */

    //    @Test
    public void displayAllVendor() {
        Result<List<Vendor>> result = adminController.displayAllVendor();
        System.out.println(result);
    }

    //            @Test
    public void addVendor() {
        try {
            Vendor vendor = new Vendor();
            vendor.setName("Sunny");
            vendor.setAddress("Nowhere");

            adminController.addVendor(vendor);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //    @Test
    public void updateVendor() {
        try {
            Vendor vendor = new Vendor();
            vendor.setId(1);
            vendor.setName("华为");
            vendor.setAddress("上海");

            adminController.updateVendor(vendor);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //        @Test
    public void removeVendor() {
        try {
            adminController.removeVendor(2);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    /* ---------------------------- 管理平台 ---------------------------- */

    //    @Test
    public void displayAllPlatform() {
        Result<List<Platform>> result = adminController.displayAllPlatform();
        System.out.println(result);
    }

    //        @Test
    public void addPlatform() {
        try {
            Platform platform = new Platform("淘宝宝");
            adminController.addPlatform(platform);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //    @Test
    public void updatePlatform() {
        try {
            Platform platform = new Platform("京东");
            platform.setId(1);

            adminController.updatePlatform(platform);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //            @Test
    public void removePlatform() {
        try {
            adminController.removePlatform(4);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    /* ---------------------------- 管理商品（类别） ---------------------------- */

    //        @Test
    public void displayAllCommodity() {
        Result<List<Commodity>> result = adminController.displayAllCommodity();
        System.out.println(result);
    }

    //    @Test
    public void addCommodity() {
        try {
            Commodity commodity = new Commodity("test", "袜子");
            adminController.addCommodity(commodity);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

//            @Test
    public void updateCommodity() {
        try {
            Commodity commodity = new Commodity("华为Mate60", "手机");
            commodity.setId(1);

            adminController.updateCommodity(commodity);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //    @Test
    public void removeCommodity() {
        try {
            adminController.removeCommodity(5);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    /* ---------------------------- 管理上架商品 ---------------------------- */

    @Test
    public void updateSaleCommodityPrice() {
        try {
            adminController.updateSaleCommodityPrice(11, 181.1F);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

}
