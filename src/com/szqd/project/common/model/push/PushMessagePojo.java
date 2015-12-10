package com.szqd.project.common.model.push;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by like on 7/8/15.
 */
public class PushMessagePojo extends PushMessageDB {

    private Boolean unexpired = false;

    public Boolean getUnexpired() {
        return unexpired;
    }

    public void setUnexpired(Boolean unexpired) {
        this.unexpired = unexpired;
    }

    public void setBeginTimeText(Date beginTimeDate)
    {
        if (beginTimeDate != null) {
            this.setBeginTime(beginTimeDate.getTime());
        }
    }

    public void setEndTimeText(Date endTimeDate)
    {
        if (endTimeDate != null)
        {
            this.setEndTime(endTimeDate.getTime());
        }
    }

    public String getMessageTypesText()
    {
        StringBuilder messageTypesText = new StringBuilder();
        List<Integer> messageTypes = this.getMessageTypes();
        
        if (messageTypes != null)
        {
            for (int i = 0; i < messageTypes.size(); i++) {
                if (i != 0)
                {
                    messageTypesText.append(",");
                }
                Integer id =  messageTypes.get(i);
                String name = MessageTypeEnum.getNameByID(id).getName();
                messageTypesText.append(name);
            }

            return messageTypesText.toString();
        }
        else{
            return "";
        }
    }

    public String getPushTypeText()
    {
        StringBuilder pushTypeText = new StringBuilder();
        Integer pushType = this.getPushType();
        if (pushType != null)
        {
            return PushTypeEnum.getNameByID(pushType);
        }
        else{
            return "";
        }
    }

    public String getBeginTimeText()
    {
        String beginTimeText = null;
        Long beginTime = this.getBeginTime();
        if (beginTime != null)
        {
            Date beginDate = new Date(beginTime);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            beginTimeText = sf.format(beginDate);
        }
        return beginTimeText;
    }

    public String getEndTimeText()
    {
        String endTimeText = null;
        Long endTime = this.getEndTime();
        if (endTime != null)
        {
            Date endDate = new Date(endTime);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            endTimeText = sf.format(endDate);
        }
        return endTimeText;
    }


}
