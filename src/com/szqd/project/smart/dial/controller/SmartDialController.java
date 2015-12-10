package com.szqd.project.smart.dial.controller;

import com.szqd.project.smart.dial.service.SmartDialService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by like on 9/18/15.
 */
@RestController
@RequestMapping("/smart-dial")
public class SmartDialController
{
    private static final String APPKEY_FOR_SMART_DIAL = "962585252";
    private SmartDialService smartDialService = null;

    public SmartDialService getSmartDialService() {
        return smartDialService;
    }

    public void setSmartDialService(SmartDialService smartDialService) {
        this.smartDialService = smartDialService;
    }

    @RequestMapping("/visit-smart-dial.do")
    public void visitSmartDial(String appkey,String ifa)
    {
        smartDialService.recordSmartDialVisitStore(appkey,ifa);
    }

    @RequestMapping("/download-smart-dial.do")
    public void downloadSmartDial(String appkey,String mac,String ifa)
    {
        smartDialService.recordSmartDialDownload(appkey,mac,ifa);
    }
}
