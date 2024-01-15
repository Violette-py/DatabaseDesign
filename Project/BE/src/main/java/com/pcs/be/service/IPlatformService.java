package com.pcs.be.service;

import com.pcs.be.entity.Platform;

import java.io.IOException;
import java.util.List;

public interface IPlatformService {
    void init() throws IOException;
    List<Platform> displayAll();
    void addPlatform(Platform platform);
    void updatePlatform(Platform platform);
    void removePlatform(Integer id);
}