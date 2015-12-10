package com.szqd.project.common.model.image;

import com.szqd.framework.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by like on 11/16/15.
 */
public class ImageEntityPOJO extends ImageEntityDB
{
    private String titleRegex = null;

    public String getTitleRegex() {
        return titleRegex;
    }

    public void setTitleRegex(String titleRegex) {
        this.titleRegex = titleRegex;
    }

    public Boolean isQueryTodayImage;

    public Boolean getIsQueryTodayImage() {
        return isQueryTodayImage;
    }

    public void setIsQueryTodayImage(Boolean isQueryTodayImage) {
        this.isQueryTodayImage = isQueryTodayImage;
    }

    public void setCreateTimeText(Date createTimeDate)
    {
        if (createTimeDate != null)
        {
            Long createTime = DateUtils.truncateDate(createTimeDate).getTimeInMillis();
            this.setCreateTime(createTime);
        }

    }

    public String getCreateTimeText()
    {
        Long createTime = this.getCreateTime();
        if (createTime != null)
        {
            Date date = new Date(createTime);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            return sf.format(date);
        }
        return null;
    }
}
