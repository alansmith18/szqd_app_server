package com.szqd.project.mobile.lock.controller;


import com.szqd.framework.controller.SpringMVCController;


import com.szqd.project.mobile.lock.model.json.TestEntity;

import com.szqd.project.mobile.lock.service.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by like on 15/1/16.
 */
@RestController
@RequestMapping("/test")
public class TestController extends SpringMVCController {

    public TestController()
    {

    }

    private TestService testService = null;

    public TestService getTestService()
    {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/test.do", method = RequestMethod.GET)
//    @ResponseBody
    public Object test(TestEntity e)
    {
        String result = this.testService.testQuery(e);
        e.setName(result);
        return result;
    }


    @RequestMapping(value = "/add-test.do", method = RequestMethod.GET)
    @ResponseBody
    public void addTest(TestEntity e1)
    {
        this.testService.testAdd(e1);

    }


}
