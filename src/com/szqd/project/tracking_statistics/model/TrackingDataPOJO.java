package com.szqd.project.tracking_statistics.model;

import com.szqd.framework.util.DateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 11/23/15.
 */
public class TrackingDataPOJO extends TrackingDataDB
{
    private String createTimeText;

    private Long numberOfClick;

    @Transient
    private Date beginDay;

    @Transient
    private Date endDay;

    public Long getNumberOfClick() {
        return numberOfClick;
    }

    public void setNumberOfClick(Long numberOfClick) {
        this.numberOfClick = numberOfClick;
    }

    public Date getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(Date beginDay)
    {
        this.beginDay = beginDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay)
    {
        this.endDay = endDay;
    }

    public List<Long> getDateListBetweenBeginToEnd()
    {
        List<Long> dateList = new ArrayList<>();
        if (beginDay == null || endDay == null)
        {
            return null;
        }

        Long diff = DateUtils.daysBetweenTwoDate(beginDay,endDay);
        Date baseDate = null;
        if (diff >= 0)
            baseDate = beginDay;
        else
            baseDate = endDay;

        dateList.add(baseDate.getTime());
        if (diff != 0)
        {
            for (int i = 1; i <= Math.abs(diff); i++)
            {
                Date dateVar = org.apache.commons.lang3.time.DateUtils.addDays(baseDate,i);
                dateList.add(dateVar.getTime());
            }
        }
        return dateList;

    }

    public String getEndDayText()
    {
        if (endDay != null)
        {
            return DateFormatUtils.format(endDay,"yyyy-MM-dd");
        }
        return null;
    }

    public String getBeginDayText()
    {
        if (beginDay != null)
        {
            return DateFormatUtils.format(beginDay,"yyyy-MM-dd");
        }
        return null;
    }

    public String getCreateTimeText()
    {
        return createTimeText;
    }

    public void setCreateTimeText()
    {
        Long createTime = this.getCreateTime();
        if (createTime != null)
        {
            this.createTimeText = DateUtils.dateToString(new Date(createTime), "MM-dd");
        }
    }


}
