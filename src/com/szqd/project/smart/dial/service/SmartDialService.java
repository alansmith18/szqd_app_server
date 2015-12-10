package com.szqd.project.smart.dial.service;

/**
 * Created by like on 9/18/15.
 */
public interface SmartDialService {
    /**
     * 记录神指拨号的商店访问
     * @param deviceID
     */
    public void recordSmartDialVisitStore(String appkey,String deviceID);

    /**
     * 记录神指拨号的下载
     * @param appkey
     * @param mac
     * @param ifa
     */
    public void recordSmartDialDownload(String appkey,String mac,String ifa);

}
