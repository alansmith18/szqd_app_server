package com.szqd.project.common.model;

/**
 * Created by like on 6/19/15.
 */
public class UninstallCountEntity
{
    public static final String ENTITY_NAME = "COMMON_UNINSTALL_COUNT_ENTITY";

    private String appName;
    private Integer count;
    private Long date;

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
