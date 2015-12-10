package com.szqd.project.mobile.lock.service;

import com.szqd.project.mobile.lock.model.json.TestEntity;

/**
 * Created by like on 15/3/11.
 */
public interface TestService
{
    public void testAdd(TestEntity e);

    public String testQuery(TestEntity e);

    public void test();
}
