package com.wangqifan.toutiao.Service;

import com.wangqifan.toutiao.util.JedisAdapter;
import com.wangqifan.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LikeService {
    @Autowired
    private JedisAdapter jedisAdapter;
    //某个用户对某个东西喜欢还是不喜欢
    //entityId传入的是newsid
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    public long like(int userId, int entityType, int entityId) {
        // 在喜欢集合里增加
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));
        // 从反对里删除
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long disLike(int userId, int entityType, int entityId) {
        // 在反对集合里增加
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
        // 从喜欢里删除
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

}
