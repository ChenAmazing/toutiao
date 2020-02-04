package com.wangqifan.toutiao.Service;

public interface RedisService {
    void saveLikedRedis(String key,String userId);

    void savedisLikedRedis(String key,String userId);

    int getLikedNumRedis(String key);

}
