package com.szqd.project.common.model.push;

/**
 * Created by like on 8/11/15.
 */
public class AppInstallDB {

    public static final String ENTITY_NAME = "COMMON_APP_INSTALL";

    /**
     * 用户id
     */
    private String userID = null;

    /**
     * appID
     */
    private String pacageName = null;

    /**
     * app的名称
     */
    private String appName = null;

    /**
     * 分类id
     */
    private Integer appCategoryID = null;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPacageName() {
        return pacageName;
    }

    public void setPacageName(String pacageName) {
        this.pacageName = pacageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAppCategoryID() {
        return appCategoryID;
    }

    public void setAppCategoryID(Integer appCategoryID) {
        this.appCategoryID = appCategoryID;
    }
}
