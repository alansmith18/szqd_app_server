package com.szqd.project.red.gift.model;


import java.io.Serializable;

/**
 * Created by like on 9/9/15.
 */
public class GiftEntityDB implements Serializable{

    transient public static final String ENTITY_NAME = "RED_GIFT_GIFT";

    private Long id = null;

    /**
     * 图标
     */
    private String icon = null;

    /**
     * 红包名称
     */
    private String giftName = null;

    /**
     * 红包信息
     */
    private String giftInfo = null;

    /**
     * 红包描述
     */
    private String giftDesc = null;

    /**
     * 红包点评
     */
    private String giftComment = null;

    /**
     * url链接
     */
    private String url = null;

    /**
     * 开始时间
     */
    private Long beginTime = null;

    /**
     * 结束时间
     */
    private Long endTime = null;

    /**
     * 创建时间
     */
    private Long createTime = null;

    /**
     * 红包类别 1. 最新  2.最热  3.坐车优惠  4.外卖优惠  5.送流量  6.购物优惠  7.其他优惠
     */
    private Integer type = null;

    /**
     * 展现形态 1.红包 2.banner
     */
    private Integer formType = null;

    /**
     * 排序的序号
     */
    private Integer orderNo = null;


    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(String giftInfo) {
        this.giftInfo = giftInfo;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }

    public String getGiftComment() {
        return giftComment;
    }

    public void setGiftComment(String giftComment) {
        this.giftComment = giftComment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
