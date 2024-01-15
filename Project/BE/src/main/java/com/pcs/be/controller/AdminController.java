package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.entity.Commodity;
import com.pcs.be.entity.Platform;
import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;
import com.pcs.be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IVendorService vendorService;
    @Autowired
    private IPlatformService platformService;
    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private ISaleCommodityService saleCommodityService;
    @Autowired
    private IHistoryPriceService historyService;

    /* ---------------------------- 初始化系统 ---------------------------- */
    @PostMapping("/initVendor")
    public <T> Result<T> initVendor() {
        Result<T> result = new Result<>();
        try {
            vendorService.init();
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/initPlatform")
    public <T> Result<T> initPlatform() {
        Result<T> result = new Result<>();
        try {
            platformService.init();
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/initCommodity")
    public <T> Result<T> initCommodity() {
        Result<T> result = new Result<>();
        try {
            commodityService.init();
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/initSaleCommodity")
    public <T> Result<T> initSaleCommodity() {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.init();
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    @PostMapping("/initHistoryPrice")
    public <T> Result<T> init() {
        Result<T> result = new Result<>();
        try {
            historyService.init();
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

//    @PostMapping("/init")
//    public <T> Result<T> init() {
//        Result<T> result = new Result<>();
//        try {
//            vendorService.init();
//            platformService.init();
//            commodityService.init();
//            saleCommodityService.init();
//            historyService.init();
//            result.succ(null);
//        } catch (Exception e) {
//            result.fail(e.getMessage());
//            System.out.println(e.getMessage());
//        }
//        return result;
//    }

    /* ---------------------------- 管理用户 ---------------------------- */

    /* 查看已有用户 */
    @RequestMapping("/displayAllUser")
    public Result<List<User>> displayAllUser() {
        Result<List<User>> result = new Result<>();
        List<User> userList = userService.displayAll();
        result.succ(userList);
        return result;
    }

    /* 新增用户 */
    @PostMapping("/addUser")
    public <T> Result<T> addUser(@RequestBody User user) {
        Result<T> result = new Result<>();
        try {
            userService.addUser(user);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 更新用户信息：可以修改名字，后端会查重 */
    @PutMapping("/updateUser")
    public <T> Result<T> updateUser(@RequestBody User user) {
        Result<T> result = new Result<>();
        try {
            userService.updateUser(user);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 删除用户 */
    @PostMapping("/removeUser")
    public <T> Result<T> removeUser(@RequestParam Integer id) {
        Result<T> result = new Result<>();
        try {
            userService.removeUser(id);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* ---------------------------- 管理商家 ---------------------------- */

    /* 查看已有商家 */
    @GetMapping("/displayAllVendor")
    public Result<List<Vendor>> displayAllVendor() {
        Result<List<Vendor>> result = new Result<>();
        List<Vendor> vendorList = vendorService.displayAll();
        result.succ(vendorList);
        return result;
    }

    /* 新增商家 */
    @PostMapping("/addVendor")
    public <T> Result<T> addVendor(@RequestBody Vendor vendor) {
        Result<T> result = new Result<>();
        try {
            vendorService.addVendor(vendor);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 更新商家信息：可以修改名字，后端会查重 */
    @PutMapping("/updateVendor")
    public <T> Result<T> updateVendor(@RequestBody Vendor vendor) {
        Result<T> result = new Result<>();
        try {
            vendorService.updateVendor(vendor);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 删除商家 */
    @PostMapping("/removeVendor")
    public <T> Result<T> removeVendor(@RequestParam Integer id) {
        Result<T> result = new Result<>();
        try {
            vendorService.removeVendor(id);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* ---------------------------- 管理平台 ---------------------------- */

    /* 查看已有平台 */
    @GetMapping("/displayAllPlatform")
    public Result<List<Platform>> displayAllPlatform() {
        Result<List<Platform>> result = new Result<>();
        List<Platform> platformList = platformService.displayAll();
        result.succ(platformList);
        return result;
    }

    /* 新增平台 */
    @PostMapping("/addPlatform")
    public <T> Result<T> addPlatform(@RequestBody Platform platform) {
        Result<T> result = new Result<>();
        try {
            platformService.addPlatform(platform);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 更新平台信息：可以修改名字，后端会查重 */
    @PutMapping("/updatePlatform")
    public <T> Result<T> updatePlatform(@RequestBody Platform platform) {
        Result<T> result = new Result<>();
        try {
            platformService.updatePlatform(platform);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 删除平台 */
    @PostMapping("/removePlatform")
    public <T> Result<T> removePlatform(@RequestParam Integer id) {
        Result<T> result = new Result<>();
        try {
            platformService.removePlatform(id);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* ---------------------------- 管理商品（类别） ---------------------------- */

    /* 查看已有商品 */
    @GetMapping("/displayAllCommodity")
    public Result<List<Commodity>> displayAllCommodity() {
        Result<List<Commodity>> result = new Result<>();
        List<Commodity> commodityList = commodityService.displayAll();
        result.succ(commodityList);
        return result;
    }

    /* 新增商品 */
    @PostMapping("/addCommodity")
    public <T> Result<T> addCommodity(@RequestBody Commodity commodity) {
        Result<T> result = new Result<>();
        try {
            commodityService.addCommodity(commodity);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 更新商品信息：可以修改名字，后端会查重 */
    @PutMapping("/updateCommodity")
    public <T> Result<T> updateCommodity(@RequestBody Commodity commodity) {
        Result<T> result = new Result<>();
        try {
            commodityService.updateCommodity(commodity);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 删除商品 */
    @PostMapping("/removeCommodity")
    public <T> Result<T> removeCommodity(@RequestParam Integer id) {
        Result<T> result = new Result<>();
        try {
            commodityService.removeCommodity(id);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* ---------------------------- 管理上架商品 ---------------------------- */

    // 查看上架商品 - UserController
    // 新增上架商品、修改上架商品基础信息、删除上架商品 - VendorController

    /* 修改上架商品价格（管理员无限次数） */
    @PutMapping("/updateSaleCommodityPrice")
    public <T> Result<T> updateSaleCommodityPrice(@RequestParam Integer id, @RequestParam Float price) {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.updatePrice(id, price, "admin");
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

}
