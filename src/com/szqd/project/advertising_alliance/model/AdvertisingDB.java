package com.szqd.project.advertising_alliance.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by like on 10/26/15.
 */
public class AdvertisingDB {

    public static final String ENTITY_NAME = "AD_ALLIANCE_ADVERTISING";

    public static final int STATUS_SUBMIT = 0;
    public static final int STATUS_IN_USE = 1;
    public static final int STATUS_FAILED = 2;
    public static final int STATUS_STOP = 3;

    private Long id;
    private String name;
    private String url;
    private String desc;
    private String pic;
    private Long uid;
    private Integer width;
    private Integer height;
    private Set<Long> channelIDList;
    private Set<Long> pendingChannelIDList;

    private ArrayList<Activation> activations = null;

    private Integer status;//0:等待审核 1:正常使用 2:停止使用
    private Long createTime;

    public ArrayList<Activation> getActivations() {
        return activations;
    }

    public void setActivations(ArrayList<Activation> activations) {
        this.activations = activations;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Long> getChannelIDList() {
        return channelIDList;
    }

    public void setChannelIDList(Set<Long> channelIDList) {
        this.channelIDList = channelIDList;
    }

    public Set<Long> getPendingChannelIDList() {
        return pendingChannelIDList;
    }

    public void setPendingChannelIDList(Set<Long> pendingChannelIDList) {
        this.pendingChannelIDList = pendingChannelIDList;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


}


