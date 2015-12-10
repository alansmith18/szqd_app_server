package com.szqd.framework.persistence;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by like on 15/1/16.
 */
public class CoreDao
{

    private RedisDao redisDao = null;

    public RedisDao getRedisDao() {
        return redisDao;
    }

    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    public RedisTemplate getRedisTemplateWithJsonSerializer()
    {
        RedisTemplate redisTemplate = redisDao.getRedisTemplateWithJsonSerializer();
        return redisTemplate;
    }



    private MongoTemplate mongoTemplate = null;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }



    private MyBatisDao myBatisDao = null;

    public MyBatisDao getMyBatisDao() {
        return myBatisDao;
    }

    public void setMyBatisDao(MyBatisDao myBatisDao) {
        this.myBatisDao = myBatisDao;
    }
}
