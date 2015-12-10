package com.szqd.project.mobile.lock.model;



/**
 * Created by like on 4/14/15.
 */
public class AbstractWallpaperEntity {

    private Long id;

    /**
     * 640*480
     */
    private String urlVga;

    /**
     * 320*240
     */
    private String urlQvga;

    /**
     *  800*600
     */
    private String urlSvga;

    /**
     * 800*480
     */
    private String urlWvga;

    /**
     * 854*480
     */
    private String urlFwvga;

    /**
     * 960*540
     */
    private String urlQhd;

    /**
     * 1280*720
     */
    private String url720p;

    /**
     * 1920*1080
     */
    private String url1080p;

    /**
     *  缩略图
     */
    private String thumbnails;


    /**
     * 壁纸类别
     */
    private Long pagerType;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 手工排序
     */
    private Integer manualOrder;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 生效时间
     */
    private Long effectiveTime;

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlVga() {
        return urlVga;
    }

    public void setUrlVga(String urlVga) {
        this.urlVga = urlVga;
    }

    public String getUrlQvga() {
        return urlQvga;
    }

    public void setUrlQvga(String urlQvga) {
        this.urlQvga = urlQvga;
    }

    public String getUrlSvga() {
        return urlSvga;
    }

    public void setUrlSvga(String urlSvga) {
        this.urlSvga = urlSvga;
    }

    public String getUrlWvga() {
        return urlWvga;
    }

    public void setUrlWvga(String urlWvga) {
        this.urlWvga = urlWvga;
    }

    public String getUrlFwvga() {
        return urlFwvga;
    }

    public void setUrlFwvga(String urlFwvga) {
        this.urlFwvga = urlFwvga;
    }

    public String getUrlQhd() {
        return urlQhd;
    }

    public void setUrlQhd(String urlQhd) {
        this.urlQhd = urlQhd;
    }

    public String getUrl720p() {
        return url720p;
    }

    public void setUrl720p(String url720p) {
        this.url720p = url720p;
    }

    public String getUrl1080p() {
        return url1080p;
    }

    public void setUrl1080p(String url1080p) {
        this.url1080p = url1080p;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Long getPagerType() {
        return pagerType;
    }

    public void setPagerType(Long pagerType) {
        this.pagerType = pagerType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getManualOrder() {
        return manualOrder;
    }

    public void setManualOrder(Integer manualOrder) {
        this.manualOrder = manualOrder;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}



