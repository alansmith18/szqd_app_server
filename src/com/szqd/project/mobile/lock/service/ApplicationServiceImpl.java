package com.szqd.project.mobile.lock.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.stereotype.Component;

/**
 * Created by like on 15/3/18.
 */
@Component
public class ApplicationServiceImpl implements ApplicationListener<ContextRefreshedEvent>
{
    private static Logger logger = Logger.getLogger(ApplicationServiceImpl.class);


    private static boolean isCalled = false;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (isCalled)
        {
            return;
        }

//        this.myWeatherService.fetchSmartWeatherCloudDataToSaveOwnDatabase("101010100");

        isCalled = true;
    }
}
