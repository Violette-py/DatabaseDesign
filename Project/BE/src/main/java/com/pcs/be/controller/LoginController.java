package com.pcs.be.controller;

import com.pcs.be.common.Result;
import com.pcs.be.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ICommonService commonService;

    @PostMapping("/login")
    public <T> Result<T> login(@RequestParam String name) {

        Result<T> result = new Result<>();
        try {
            List<Integer> resultList = commonService.login(name);

            result.setRole(resultList.get(0));
            result.setId(resultList.get(1));
            result.succ(null);
        } catch (Exception e) {
            result.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
        return result;
    }
}
