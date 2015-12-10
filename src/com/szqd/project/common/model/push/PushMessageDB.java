package com.szqd.project.common.model.push;

import java.io.Serializable;
import java.util.List;

/**
 * 消息推送
 */
public class PushMessageDB implements Serializable {

    public static final String ENTITY_NAME = "COMMON_PUSH_MESSAGE";

    private Long id;

    /**
     * 小图标
     */
    private String icon;

    /**
     * 内容图片
     */
    private String contentPic;

    /**
     * app名称
     */
    private String appName;

    /**
     * 主标题
     */
    private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 所属项目
     */
    private Integer project;

    /**
     * 目标地址
     */
    private String targetURL;

    /**
     * url类型
     */
    private Integer urlType;

    /**
     * 包名
     */
    private String packageName = null;

    /**
     * 消息类型(多个)
     */
    private List<Integer> messageTypes;

    /**
     * 推送形式
     */
    private Integer pushType = null;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 排序字段
     */
    private Integer orderBy;

    /**
     * 开始时间
     */
    private Long beginTime;

    /**
     * 结束时间
     */
    private Long endTime;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContentPic() {
        return contentPic;
    }

    public void setContentPic(String contentPic) {
        this.contentPic = contentPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public List<Integer> getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(List<Integer> messageTypes) {
        this.messageTypes = messageTypes;
    }


}
