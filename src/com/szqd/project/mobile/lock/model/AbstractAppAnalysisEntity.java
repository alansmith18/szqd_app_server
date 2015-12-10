package com.szqd.project.mobile.lock.model;

import org.bson.types.ObjectId;

/**
 * Created by like on 3/27/15.
 */
public class AbstractAppAnalysisEntity {



    /**
     * 启动app的时间点
     */
    private Long lauchTime;

    /**
     * app退出时间
     */
    private Long exitTime;

    /**
     * 使用的时长
     */
    private Long howLongUse;

    /**
     * 使用时长分类
     */
    private Integer howLongUseCategory;

    /**
     * 用户id
     */
    private String userID = null;

    /**
     * appID
     */
    private String appID = null;

    /**
     * app的名称
     */
    private String appName = null;

    /**
     * 分类id
     */
    private Integer appCategoryID = null;

    /**
     * 启动的小时数
     */
    private Integer lauchTimeHours = null;



    public Integer getLauchTimeHours() {
        return lauchTimeHours;
    }

    public void setLauchTimeHours(Integer lauchTimeHours) {
        this.lauchTimeHours = lauchTimeHours;
    }

    public Integer getHowLongUseCategory() {
        return howLongUseCategory;
    }

    public void setHowLongUseCategory(Integer howLongUseCategory) {
        this.howLongUseCategory = howLongUseCategory;
    }

    public Long getLauchTime() {
        return lauchTime;
    }

    public void setLauchTime(Long lauchTime) {
        this.lauchTime = lauchTime;
    }

    public Long getExitTime() {
        return exitTime;
    }

    public void setExitTime(Long exitTime) {
        this.exitTime = exitTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public Long getHowLongUse() {
        return howLongUse;
    }

    public void setHowLongUse(Long howLongUse) {
        this.howLongUse = howLongUse;
    }

    public Integer getAppCategoryID() {
        return appCategoryID;
    }

    public void setAppCategoryID(Integer appCategoryID) {
        this.appCategoryID = appCategoryID;
    }
}
