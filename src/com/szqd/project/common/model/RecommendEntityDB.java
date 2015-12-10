package com.szqd.project.common.model;

import java.util.List;

/**
 * Created by like on 5/11/15.
 */
public class RecommendEntityDB {

    public static final String ENTITY_NAME = "COMMON_RECOMMEND";

    private Long id;
    private String bannerURL = null;
    private String icon = null;
    private String pic = null;
    private String appName = null;
    private String dialogTitle = null;
    private String shortDesc = null;
    private List<Integer> platform = null;

    private List<Integer> netOperators = null;
    private String packageName = null;
    private String downloadURL = null;
    private List<Integer> recommendCategory = null;
    private List<Integer> projects = null;
    private Integer status = null;
    private Integer mark;
    private Integer otherMark;
    private Long createTime;
    private Long modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }




    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }



    public List<Integer> getProjects() {
        return projects;
    }

    public void setProjects(List<Integer> projects) {
        this.projects = projects;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getOtherMark() {
        return otherMark;
    }

    public void setOtherMark(Integer otherMark) {
        this.otherMark = otherMark;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<Integer> getNetOperators() {
        return netOperators;
    }

    public void setNetOperators(List<Integer> netOperators) {
        this.netOperators = netOperators;
    }

    public List<Integer> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Integer> platform) {
        this.platform = platform;
    }

    public List<Integer> getRecommendCategory() {
        return recommendCategory;
    }

    public void setRecommendCategory(List<Integer> recommendCategory) {
        this.recommendCategory = recommendCategory;
    }
}
