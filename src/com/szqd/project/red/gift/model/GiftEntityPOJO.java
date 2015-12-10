package com.szqd.project.red.gift.model;

import com.szqd.framework.model.SelectEntity;
import org.springframework.data.annotation.Transient;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 9/9/15.
 */
public class GiftEntityPOJO extends GiftEntityDB {
    public void setBeginTimeText(Date beginTimeDate){
        if (beginTimeDate != null) this.setBeginTime(beginTimeDate.getTime());
    }

    public void setEndTimeText(Date endTimeDate){
        if (endTimeDate != null) this.setEndTime(endTimeDate.getTime());
    }

    public String getBeginTimeText()
    {
        String beginTimeText = null;
        Long beginTime = this.getBeginTime();
        if (beginTime != null) {
            Date date = new Date(beginTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            beginTimeText = dateFormat.format(date);
        }
        return beginTimeText;
    }


    public String getEndTimeText()
    {
        String endTimeText = null;
        Long endTime = this.getEndTime();
        if (endTime != null)
        {
            Date date = new Date(endTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            endTimeText = dateFormat.format(date);
        }
        return endTimeText;
    }

    /**
     * 是否过滤过期
     */
    @Transient
    private Boolean isValidateExpire;

    public Boolean getIsValidateExpire() {
        return isValidateExpire;
    }

    public void setIsValidateExpire(Boolean isValidateExpire) {
        this.isValidateExpire = isValidateExpire;
    }

    /**
     * 模糊查询红包名称的条件
     */
    @Transient
    private String giftNameRegex;

    public String getGiftNameRegex() {
        return giftNameRegex;
    }

    public void setGiftNameRegex(String giftNameRegex) {
        this.giftNameRegex = giftNameRegex;
    }

    @Transient
    private String giftInfoRegex;

    @java.beans.Transient
    public String getGiftInfoRegex() {
        return giftInfoRegex;
    }

    public void setGiftInfoRegex(String giftInfoRegex) {
        this.giftInfoRegex = giftInfoRegex;
    }

    @java.beans.Transient
    @Transient
    public List<SelectEntity> getTypeList()
    {
        return Arrays.asList(new SelectEntity("最新","1"),new SelectEntity("电影优惠","2"),new SelectEntity("坐车优惠","3"),
                new SelectEntity("外卖优惠","4"),new SelectEntity("送流量","5"),
                new SelectEntity("购物优惠","6"),new SelectEntity("其他优惠","7"));

    }

    @Transient
    @java.beans.Transient
    public List<SelectEntity> getFormTypeList()
    {
        return Arrays.asList(new SelectEntity("红包","1"),new SelectEntity("banner","2"));
    }

    /**
     * 排序类型 1.创建时间 2.orderNO
     */
    @Transient
    private Integer sortType;

    @java.beans.Transient
    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
