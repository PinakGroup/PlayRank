package com.wzx.service.impl;

import com.wzx.service.MsgService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author arthurwang
 * @date 2019-08-01
 */
@Service
public class MsgServiceImpl implements MsgService {
    private final StringRedisTemplate stringRedisTemplate;

    public MsgServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String setMsg(String key, String msg) {
        stringRedisTemplate.opsForValue().set(key,msg);
        return "success";
    }

    @Override
    public String getMsg(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
