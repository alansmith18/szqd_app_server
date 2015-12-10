package com.szqd.project.popularize.analysis.model;

/**
 * Created by like on 4/3/15.
 */
public abstract class AbstractAppInfoEntity
{

    private String appID;
    private String appName;



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
}
