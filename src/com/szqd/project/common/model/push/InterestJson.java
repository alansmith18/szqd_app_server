package com.szqd.project.common.model.push;

/**
 * Created by like on 8/13/15.
 */
public class InterestJson
{
    private long useTime;
    private int launchTimes;
    private String appName;

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public int getLaunchTimes() {
        return launchTimes;
    }

    public void setLaunchTimes(int launchTimes) {
        this.launchTimes = launchTimes;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
