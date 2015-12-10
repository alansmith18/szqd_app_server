package com.szqd.project.smart.dial.model;

/**
 * Created by like on 9/18/15.
 */
public class SmartDialUserDownloadDB {
    public static final String ENTITY_NAME = "SMART_DIAL_USER_DOWNLOAD";

    private String id;
    private String deviceID;
    private Long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
