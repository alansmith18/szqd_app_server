package com.szqd.project.popularize.analysis.model;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 3/31/15.
 */
public class AppActivationEntity extends AbstractAppActivationEntity{

    private static final Logger logger = Logger.getLogger(AppActivationEntity.class);
    public static final String ENTITY_NAME = "AppActivationEntity";

    private String plateformName = null;

    public String getPlateformName() {
        return plateformName;
    }

    public void setPlateformName(String plateformName) {
        this.plateformName = plateformName;
    }

    /**
     * 用户数量
     */
    private Integer userCount;

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    private float incremental = 0;
    private float scale = 1;

    public float getIncremental() {
        return incremental;
    }

    public void setIncremental(float incremental) {
        this.incremental = incremental;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * 经过公式计算后的用户数量
     */
    public Integer getCalculateUserCount() {

        int calculateUserCount = (int)(this.getScale() * this.getUserCount() + this.getIncremental());
        return calculateUserCount;
    }



    private String beginDate;

    private String endDate;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getBeginDateLongType()
    {
        if (this.beginDate == null)
            return null;

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sf.parse(this.getBeginDate()).getTime();
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.model.AppActivationEntity.getBeginDateLongType()",e);
            throw new RuntimeException("获取页面的开始时间出错");
        }

    }

    public Long getEndDateLongType()
    {
        if (this.endDate == null)
            return null;

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sf.parse(this.getEndDate()).getTime();
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.model.AppActivationEntity.getBeginDateLongType()",e);
            throw new RuntimeException("获取页面的开始时间出错");
        }

    }

    public String getCreateTimeText()
    {
        Long createTime = this.getCreateTime();
        Date createDate = new Date(createTime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        String createDateString = sf.format(createDate);

        return createDateString;
    }
}
