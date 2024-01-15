package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.entity.SaleCommodity;
import com.pcs.be.entity.Vendor;
import com.pcs.be.service.ISaleCommodityService;
import com.pcs.be.service.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private IVendorService vendorService;

    @Autowired
    private ISaleCommodityService saleCommodityService;

    /* 查看商家个人信息 */
    @GetMapping("/displayInfo")
    public Result<Vendor> displayInfo(@RequestParam Integer id) {
        Result<Vendor> result = new Result<>();
        Vendor vendor = vendorService.displayOne(id);
        result.succ(vendor);
        return result;
    }

    /* 查看该商家的所有上架商品 */
    @GetMapping("/displayAllInOneShop")
    public Result<List<SaleCommodity>> displayAllInOneShop(@RequestParam String vendorName) {
        Result<List<SaleCommodity>> result = new Result<>();
        List<SaleCommodity> saleCommodityList = saleCommodityService.displayAllInOneShop(vendorName);
        result.succ(saleCommodityList);
        return result;
    }

    /* 新增上架商品 */
    @PostMapping("/addSaleCommodity")
    public <T> Result<T> addSaleCommodity(@RequestBody SaleCommodity saleCommodity) {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.addSaleCommodity(saleCommodity);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 删除上架商品 */
    @PostMapping("/removeSaleCommodity")
    public <T> Result<T> removeSaleCommodity(@RequestParam Integer id) {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.removeSaleCommodity(id);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 修改上架商品基础信息 */
    @PutMapping("/updateBasicInfo")
    public <T> Result<T> updateBasicInfo(@RequestBody SaleCommodity saleCommodity) {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.updateBasicInfo(saleCommodity);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 修改上架商品价格（对于一个上架商品，商家每天最多修改一次价格） */
    @PutMapping("/updateSaleCommodityPrice")
    public <T> Result<T> updateSaleCommodityPrice(@RequestParam Integer id, @RequestParam Float price) {
        Result<T> result = new Result<>();
        try {
            saleCommodityService.updatePrice(id, price, "vendor");
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }
}
