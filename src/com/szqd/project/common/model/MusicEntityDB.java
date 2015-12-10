package com.szqd.project.common.model;

import java.util.List;

/**
 * Created by like on 5/26/15.
 */
public class MusicEntityDB {

    public static final String ENTITY_NAME = "COMMON_MUSIC";

    private Long id;

    private String name;

    private Integer status;

    private List<Integer> platform;

    private List<Integer> projects;

    private String musicFilePath;

    private Long createTime;

    private Long modifyTime;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Integer> platform) {
        this.platform = platform;
    }

    public List<Integer> getProjects() {
        return projects;
    }

    public void setProjects(List<Integer> projects) {
        this.projects = projects;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public void setMusicFilePath(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }
}
