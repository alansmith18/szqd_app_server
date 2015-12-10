package com.szqd.project.popularize.analysis.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by like on 4/3/15.
 */
public class ApplicationService implements ApplicationListener<ContextRefreshedEvent>
{
    private static boolean isCalled = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (isCalled)
        {
            return;
        }



        isCalled = true;
    }
}
