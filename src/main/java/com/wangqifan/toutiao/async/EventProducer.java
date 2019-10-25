package com.wangqifan.toutiao.async;


import com.alibaba.fastjson.JSONObject;
import com.wangqifan.toutiao.util.JedisAdapter;
import com.wangqifan.toutiao.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

//    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);
    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
//            logger.error("EventProducer发生了错误");
            return false;
        }
    }
}
