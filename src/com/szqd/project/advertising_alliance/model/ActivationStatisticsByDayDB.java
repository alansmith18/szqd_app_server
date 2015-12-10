package com.szqd.project.advertising_alliance.model;

/**
 * Created by like on 10/23/15.
 */
public class ActivationStatisticsByDayDB
{
    public static final String ENTITY_NAME = "AD_ALLIANCE_ACTIVATION_STATISTICS_BY_DAY";

    private Long id = null; //id为广告主id+渠道id+当前时间(天,忽略小时分秒毫秒)

    /**
     * 广告主id
     */
    private Long advertiserID;

    /**
     * 渠道id
     */
    private Long channelID;

    /**
     * 激活数量
     */
    private Long activationNumber;

    /**
     * 创建时间
     */
    private Long createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdvertiserID() {
        return advertiserID;
    }

    public void setAdvertiserID(Long advertiserID) {
        this.advertiserID = advertiserID;
    }

    public Long getChannelID() {
        return channelID;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public Long getActivationNumber() {
        return activationNumber;
    }

    public void setActivationNumber(Long activationNumber) {
        this.activationNumber = activationNumber;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
