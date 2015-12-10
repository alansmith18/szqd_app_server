package com.szqd.framework.persistence;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by like on 4/9/15.
 */
public class RedisDao
{
    private RedisTemplate redisTemplateWithJsonSerializer = null;


    public RedisTemplate getRedisTemplateWithJsonSerializer()
    {
        return redisTemplateWithJsonSerializer;
    }

    public void setRedisTemplateWithJsonSerializer(RedisTemplate redisTemplateWithJsonSerializer) {
        this.redisTemplateWithJsonSerializer = redisTemplateWithJsonSerializer;
    }

}
