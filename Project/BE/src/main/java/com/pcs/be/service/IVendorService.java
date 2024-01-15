package com.pcs.be.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.be.entity.User;
import com.pcs.be.entity.Vendor;

import java.io.IOException;
import java.util.List;

public interface IVendorService extends IService<Vendor> {

    void init() throws IOException;
    Vendor displayOne(Integer id);
    List<Vendor> displayAll();
    void addVendor(Vendor vendor);
    void updateVendor(Vendor vendor);
    void removeVendor(Integer id);
}
