package com.szqd.project.mobile.lock.service;


import com.szqd.framework.service.SuperService;
import com.szqd.project.mobile.lock.model.json.TestEntity;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by like on 15/3/11.
 */
public class TestServiceImpl extends SuperService implements TestService
{
    private static Logger logger = Logger.getLogger(TestServiceImpl.class);

    public void testAdd(TestEntity e)
    {
        try
        {
            ValueOperations valueOperations = this.getCoreDao().getRedisTemplateWithJsonSerializer().opsForValue();
            valueOperations.set(e.getId(),e.getName());
        } catch (Exception e1)
        {
            logger.error("com.szqd.project.mobile.lock.service.TestServiceImpl.testAdd :\n", e1);
            throw new RuntimeException("testQuery error");
        }

    }

    public String testQuery(TestEntity e)
    {
        String value = null;
        try {
            ValueOperations valueOperations = this.getCoreDao().getRedisTemplateWithJsonSerializer().opsForValue();
            value = (String)valueOperations.get(e.getId());
        } catch (Exception e1)
        {
            logger.error("com.szqd.project.mobile.lock.service.TestServiceImpl.testQuery :\n",e1);
            throw new RuntimeException("testQuery error");
        }
        return value;
    }

    public void test()
    {
        RuntimeException e = new RuntimeException("运行时异常");
        logger.error("有异常",e);
//        throw e;
    }
}
