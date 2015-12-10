package com.szqd.project.common.model.push;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 7/30/15.
 */
public class NoticeFromClientPojo extends NoticeFromClientDB
{
    private List<Integer> categoriesCondtion = null;

    public List<Integer> getCategoriesCondtion() {
        return categoriesCondtion;
    }

    public void setCategoriesCondtion(List<Integer> categoriesCondtion) {
        this.categoriesCondtion = categoriesCondtion;
    }

    private String beginDateCondition;

    private String endDateCondition;

    public String getBeginDateCondition() {
        return beginDateCondition;
    }

    public void setBeginDateCondition(String beginDateCondition) {
        this.beginDateCondition = beginDateCondition;
    }

    public String getEndDateCondition() {
        return endDateCondition;
    }

    public void setEndDateCondition(String endDateCondition) {
        this.endDateCondition = endDateCondition;
    }

    public String getCategoryName()
    {
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getNameByID(this.getCategoryID());
        if (messageTypeEnum == null)
        {
            return null;
        }
        return messageTypeEnum.getName();
    }

    public String getCreateTimeText()
    {
        Long createTime = this.getCreateTime();
        if (createTime == null) return null;
        Date createDate = new Date(createTime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(createDate);
    }
}
