package com.wangqifan.toutiao;

import com.wangqifan.toutiao.Service.RedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ToutiaoApplicationTests {
//    Logger logger = LoggerFactory.getLogger(ToutiaoApplicationTests.class);
    @Autowired
    RedisServiceImpl redisService;
    @Test
    public void test(){
        //测试redis连接
        JedisPool pool = new JedisPool("192.168.142.102", 6379);
        Jedis jedis = pool.getResource();
        System.out.println(jedis.ping());
        jedis.close();
//        redisService.saveLikedRedis("LikeTest","789");
    }

}
