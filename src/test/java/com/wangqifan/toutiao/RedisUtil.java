package com.wangqifan.toutiao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lgz
 */
public class RedisUtil {

    private static Jedis jedis = null;

    public static synchronized Jedis getJedis() {
        if (jedis == null) {
            jedis = new Jedis("192.168.142.102", 6379);
            jedis.auth("redis1234");
        }
        return jedis;
    }

    public static boolean existKey(String key) {
        return getJedis().exists(key);
    }

    public static void delKey(String key) {
        getJedis().del(key);
    }

    public static String stringGet(String key) {
        return getJedis().get(key);
    }

    public static String stringSet(String key, String value) {
        return getJedis().set(key, value);
    }

    public static void hashSet(String key, String field, String value) {
        getJedis().hset(key, field, value);
    }
}
