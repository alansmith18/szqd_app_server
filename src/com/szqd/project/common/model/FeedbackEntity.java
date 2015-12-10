package com.szqd.project.common.model;

import org.bson.types.ObjectId;

/**
 * Created by like on 4/20/15.
 */
public class FeedbackEntity {

    public static final String ENTITY_NAME = "FeedbackEntity";

    private ObjectId id;

    /**
     * 反馈信息
     */
    private String message;

    /**
     * 联系方式
     */
    private String contactInformation;

    /**
     * app版本
     */
    private String appVersion;

    /**
     * app名称
     */
    private String appName;

    /**
     * 系统版本号
     */
    private String osVerion;

    /**
     * 系统类型(ios:1,android:0)
     */
    private Integer platform;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 手机型号
     */
    private String phoneModel;

    /**
     * 性别
     */
    private Integer userSex;

    /**
     * 用户年龄
     */
    private Integer userAge;

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }



    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getOsVerion() {
        return osVerion;
    }

    public void setOsVerion(String osVerion) {
        this.osVerion = osVerion;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
