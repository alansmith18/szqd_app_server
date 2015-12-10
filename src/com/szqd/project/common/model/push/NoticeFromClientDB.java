package com.szqd.project.common.model.push;

/**
 * Created by like on 7/29/15.
 */
public class NoticeFromClientDB {

    public static final String ENTITY_NAME = "COMMON_CLIENT_NOTICE";

    /**
     * 用户id
     */
    private String userID;

    /**
     * appID
     */
    private String appID;

    /**
     * app名称
     */
    private String appName;

    /**
     * 分类id
     */
    private Integer categoryID;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Long createTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
