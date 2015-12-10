package com.szqd.project.common.model;

/**
 * Created by like on 5/13/15.
 */
public class RecommendCategoryEntityDB {

    public static final String ENTITY_NAME = "COMMON_RECOMMEND_CATEGORY";

    private Long id = null;

    private String name = null;

    private String desc = null;

    private Integer platform = null;

    private Integer project = null;

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

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }
}
