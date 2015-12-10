package com.szqd.project.common.model;

import java.util.List;

/**
 * Created by like on 5/19/15.
 */
public class AdvertisingEntityDB {

    public static final String ENTITY_NAME = "COMMON_ADVERTISING";

    private Long id;

    private String name;

    /**
     *  广告类型:AdvertisingTypeEnum
     */
    private Integer type;

    /**
     * 网络类型:NetOperatorsEnum
     */
    private List<Integer> networkType;

    private List<Integer> projects;

    private String targetURL;

    private String picPath;

    /**
     * 平台:PlatformEnum
     */
    private List<Integer> platform;

    /**
     * 执行时间
     */
    private Integer duration = null;

    /**
     * 大小类型
     */
    private Integer frameType = null;

    /**
     * 点击类型
     */
    private Integer clickType = null;

    private Long effectiveStartTime = null;

    private Long effectiveEndTime = null;

    private Integer playTime = null;

    /**
     * 报名
     */
    private String packageName = null;

    /**
     * 描述
     */
    private String desc = null;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getClickType() {
        return clickType;
    }

    public void setClickType(Integer clickType) {
        this.clickType = clickType;
    }

    public Integer getFrameType() {
        return frameType;
    }

    public void setFrameType(Integer frameType) {
        this.frameType = frameType;
    }

    public List<Integer> getNetworkType() {
        return networkType;
    }

    public void setNetworkType(List<Integer> networkType) {
        this.networkType = networkType;
    }

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    public Long getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Long effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Long getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Long effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Integer> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Integer> platform) {
        this.platform = platform;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getProjects() {
        return projects;
    }

    public void setProjects(List<Integer> projects) {
        this.projects = projects;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
