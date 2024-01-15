package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.dto.FavorSaleCommodityDTO;
import com.pcs.be.dto.FavorDTO;
import com.pcs.be.entity.*;
import com.pcs.be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private ISaleCommodityService saleCommodityService;
    @Autowired
    private IFavorService favorService;
    @Autowired
    private IHistoryPriceService historyPriceService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IPlatformService platformService;

    /* 查看用户个人信息 */
    @GetMapping("/displayInfo")
    public Result<User> displayInfo(@RequestParam Integer id) {
        Result<User> result = new Result<>();
        User user = userService.displayOne(id);
        result.succ(user);
        return result;
    }

    /* 搜索上架商品 */
    /* 后端是对上架商品名称和上架商品种类进行模糊搜索 */
    @GetMapping("/search")
    public Result<List<SaleCommodity>> search(@RequestParam String name) {
        Result<List<SaleCommodity>> result = new Result<>();
        List<SaleCommodity> saleCommodityList = saleCommodityService.search(name);
        result.succ(saleCommodityList);
        return result;
    }

    /* 获取已有平台，便于用户选择 */
    @GetMapping("/displayAllPlatform")
    public Result<List<Platform>> displayAllPlatform() {
        Result<List<Platform>> result = new Result<>();
        List<Platform> platformList = platformService.displayAll();
        result.succ(platformList);
        return result;
    }

    /* 搜索指定平台的上架商品，平台如果是多选的话，则多次调用此接口（或者修改后端代码解析逗号分隔的字符串，再多次调用service层函数也可以） */
    @GetMapping("/searchInPlatform")
    public Result<List<SaleCommodity>> searchInPlatform(@RequestParam String name, @RequestParam String platformName) {
        Result<List<SaleCommodity>> result = new Result<>();

        // 检查是否有多个平台
        String[] platformNames = platformName.split(",");
        List<SaleCommodity> saleCommodityList = new ArrayList<>();

        // 遍历每个平台名，调用service层函数
        for (String singlePlatform : platformNames) {
            List<SaleCommodity> platformSaleCommodityList = saleCommodityService.searchInPlatform(name, singlePlatform.trim());
            saleCommodityList.addAll(platformSaleCommodityList);
        }

        result.succ(saleCommodityList);
        return result;
    }

    /* 查看所有上架商品简略信息 */
    @GetMapping("/displayAllSaleCommodity")
    public Result<List<SaleCommodity>> displayAllSaleCommodity() {
        Result<List<SaleCommodity>> result = new Result<>();
        List<SaleCommodity> saleCommodityList = saleCommodityService.displayAll();
        result.succ(saleCommodityList);
        return result;
    }

    /* 查看单个上架商品详细信息 */
    @GetMapping("/displayOneSaleCommodity")
    public Result<SaleCommodity> displayOneSaleCommodity(@RequestParam Integer id) {
        Result<SaleCommodity> result = new Result<>();
        SaleCommodity saleCommodity = saleCommodityService.displayOne(id);
        result.succ(saleCommodity);
        return result;
    }

    /* 收藏上架商品 */
    @PostMapping("/addFavor")
    public <T> Result<T> addFavor(@RequestBody Favor favor) {
        Result<T> result = new Result<>();
        try {
            favorService.addFavor(favor);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 为收藏的商品设定价格下限 */
    @PutMapping("/setMinPrice")
    public <T> Result<T> setMinPrice(@RequestBody Favor favor) {
        Result<T> result = new Result<>();
        try {
            favorService.setMinPrice(favor);
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }

    /* 查看收藏的上架商品 */
    @GetMapping("/displayFavor")
    public Result<List<FavorDTO>> displayFavor(@RequestParam Integer userId) {
        Result<List<FavorDTO>> result = new Result<>();
        List<FavorDTO> favorList = favorService.displayAll(userId);
        result.succ(favorList);
        return result;
    }

    /* 查看消息列表 */
    @GetMapping("/displayMessage")
    public Result<List<Message>> displayMessage(@RequestParam Integer userId) {
        Result<List<Message>> result = new Result<>();
        List<Message> messageList = messageService.displayAll(userId);
        result.succ(messageList);
        return result;
    }

    /* ---------------------------- 进阶查询 ---------------------------- */


    /* ---- 查询某一上架商品历史价格 ---- */
    /* 参数timeSpan = "1 week" or "1 month" or "1 year" */
    @GetMapping("/displayHistoryPrice")
    public Result<List<HistoryPrice>> displayHistoryPrice(@RequestParam Integer saleCommodityId, @RequestParam String timeSpan) {
        Result<List<HistoryPrice>> result = new Result<>();
        List<HistoryPrice> historyPriceList = historyPriceService.getHistoryPrice(saleCommodityId, timeSpan);
        result.succ(historyPriceList);
        return result;
    }


    /* ---- 统计不同用户所收藏上架商品的分布情况，分析年度热点上架商品 ---- */

    /* 返回所有年龄段的统计情况，每个年龄段的统计结果包装在 FavorSaleCommodityDTO */
    @GetMapping("/displayFavorDistributionByAgeRange")
    public Result<List<FavorSaleCommodityDTO>> displayFavorDistributionByAgeRange() {
        Result<List<FavorSaleCommodityDTO>> result = new Result<>();
        List<FavorSaleCommodityDTO> favorSaleCommodityDTOList = new ArrayList<>();
        favorSaleCommodityDTOList.add(favorService.displayFavorDistributionByAgeRange(0, 30)); // 左闭右开区间
        favorSaleCommodityDTOList.add(favorService.displayFavorDistributionByAgeRange(30, 60));
        favorSaleCommodityDTOList.add(favorService.displayFavorDistributionByAgeRange(60, 150));
        result.succ(favorSaleCommodityDTOList);
        return result;
    }

    /* 返回收藏排名前三的上架商品 */
    @GetMapping("/displayTopThreeFavorSaleCommodity")
    public Result<List<SaleCommodity>> displayTopThreeFavorSaleCommodity() {
        Result<List<SaleCommodity>> result = new Result<>();
        List<SaleCommodity> saleCommodityList = favorService.displayTopThreeFavorSaleCommodity();
        result.succ(saleCommodityList);
        return result;
    }

    /* ---- 统计每种商品各个商家的价格、在不同时间跨度上的价格差，分析价格差最大最小的商品 ---- */
    /* 接口在前面写好了：先搜索指定平台的上架商品，然后价格历史还是用前面的接口拿 */


    /* ---- 统计每种商品在不同商家之间价格差，分析商家间的价格差异情况 ---- */

    /* 第一个页面：展示所有商品（种类）*/
    @GetMapping("/displayAllCommodity")
    public Result<List<Commodity>> displayAllCommodity() {
        Result<List<Commodity>> result = new Result<>();
        List<Commodity> commodityList = commodityService.displayAll();
        result.succ(commodityList);
        return result;
    }

    /* 进入每个商品种类，可以看到所有属于该种类的上架商品（不限商家和平台） */
    @GetMapping("/displayAllOfOneCommodity")
    public Result<List<SaleCommodity>> displayAllOfOneCommodity(@RequestParam Integer commodityId) {
        Result<List<SaleCommodity>> result = new Result<>();
        List<SaleCommodity> saleCommodityList = saleCommodityService.displayAllOfOneCommodity(commodityId);
        result.succ(saleCommodityList);
        return result;
    }


    /* ---- 统计年度最热门的平台（收藏商品数量最多） ---- */

    /* 返回最热门平台的名字列表 */
    @GetMapping("/displayTopThreeFavorPlatform")
    public Result<List<String>> displayTopThreeFavorPlatform() {
        Result<List<String>> result = new Result<>();
        List<String> platformNameList = favorService.displayTopThreeFavorPlatform();
        result.succ(platformNameList);
        return result;
    }
}