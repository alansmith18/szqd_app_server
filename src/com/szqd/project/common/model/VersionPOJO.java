package com.szqd.project.common.model;

/**
 * Created by like on 11/17/15.
 */
public class VersionPOJO extends VersionEntity
{
    private String appNameRegex = null;

    public String getAppNameRegex() {
        return appNameRegex;
    }

    public void setAppNameRegex(String appNameRegex) {
        this.appNameRegex = appNameRegex;
    }
}
