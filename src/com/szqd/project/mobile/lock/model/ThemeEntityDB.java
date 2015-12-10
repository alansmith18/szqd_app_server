package com.szqd.project.mobile.lock.model;

/**
 * Created by like on 5/5/15.
 */
public class ThemeEntityDB {
    public static final String ENTITY_NAME = "MOBILE_LOCK_THEME";

    private Long id = null;

    private String name = null;

    private String url = null;

    private String thumbnails;

    private Long createTime = null;

    private Integer downloadCount = null;



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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}
