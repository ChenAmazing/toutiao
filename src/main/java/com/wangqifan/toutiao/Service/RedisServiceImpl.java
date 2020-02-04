package com.wangqifan.toutiao.Service;

import com.wangqifan.toutiao.util.RedisKeyUtil;
import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {
    private final static String hashkey = "LIKED_DISLIKED";

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void saveLikedRedis(String key, String userId) {
        HashSet<String> set_liked_userId;
        if(((HashSet<String>) redisTemplate.opsForHash().get(hashkey,key))==null){
            set_liked_userId = new HashSet<>();
        }else {
            set_liked_userId = (HashSet<String>) redisTemplate.opsForHash().get(hashkey, key);
        }
        set_liked_userId.add(userId);
        redisTemplate.opsForHash().put(hashkey,key,set_liked_userId);
    }

    @Override
    public void savedisLikedRedis(String key, String userId) {
        HashSet<String> set_disliked_userId;
        if(((HashSet<String>) redisTemplate.opsForHash().get(hashkey,key))==null){
            set_disliked_userId = new HashSet<>();
        }else {
            set_disliked_userId = (HashSet<String>) redisTemplate.opsForHash().get(hashkey, key);
        }
        set_disliked_userId.add(userId);
        redisTemplate.opsForHash().put(hashkey,key,set_disliked_userId);
    }

    @Override
    public int getLikedNumRedis(String key) {
        HashSet<String> set_userId = (HashSet<String>) redisTemplate.opsForHash().get(hashkey, key);
        return set_userId.size();
    }


}
