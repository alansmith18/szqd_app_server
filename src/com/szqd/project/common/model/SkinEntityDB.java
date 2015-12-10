package com.szqd.project.common.model;

import java.util.List;

/**
 * Created by like on 5/18/15.
 */
public class SkinEntityDB {

    public static final String ENTITY_NAME = "COMMON_SKIN";

    private Long id;
    private String name;
    private String desc;
    private List<Integer> platforms;
    private List<Integer> projects;
    private Integer status;
    private Integer number;
    private String fontColor;
    private String brightnessColor;
    private Integer isRecommend;
    private String pic;
    private String skinPackagePath;
    private Long createTime;
    private Long modifyTime;


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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getBrightnessColor() {
        return brightnessColor;
    }

    public void setBrightnessColor(String brightnessColor) {
        this.brightnessColor = brightnessColor;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSkinPackagePath() {
        return skinPackagePath;
    }

    public void setSkinPackagePath(String skinPackagePath) {
        this.skinPackagePath = skinPackagePath;
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
}
