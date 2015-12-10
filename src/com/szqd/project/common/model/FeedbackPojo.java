package com.szqd.project.common.model;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by like on 4/25/15.
 */
public class FeedbackPojo extends FeedbackEntity {

    private static final Logger LOGGER = Logger.getLogger(FeedbackPojo.class);
    public String getPlatformText()
    {
        Integer platform = this.getPlatform();
        if (platform == null) return null;
        switch (platform)
        {
            case 0:
                return "ANDROID";
            case 1:
                return "IOS";
        }
        return null;
    }

    public String getFeedbackTimeText()
    {
        try {
            Long createTime = this.getCreateTime();
            if (createTime != null)
            {
                Date date = new Date(createTime);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sf.format(date);
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.model.FeedbackPojo.getFeedbackTimeText()",e);
        }
        return null;
    }

    public String getUserSexText()
    {
        Integer userSex = this.getUserSex();
        if (userSex == null)
        {
            return "";
        }
        else if (userSex.equals(new Integer(0)))
        {
            return "美女";
        }
        else if (userSex.equals(new Integer(1)))
        {
            return "帅哥";
        }
        else {
            return "妖怪";
        }
    }

    public String getUserAgeText()
    {
        Integer userAge = this.getUserAge();
        if (userAge == null)
        {
            return "";
        }
        else if (userAge.equals(new Integer(0)))
        {
            return "18-25";
        }
        else if (userAge.equals(new Integer(1)))
        {
            return "0-17";
        }
        else if (userAge.equals(new Integer(2)))
        {
            return "36-45";
        }
        else if (userAge.equals(new Integer(3)))
        {
            return "26-35";
        }
        else if (userAge.equals(new Integer(4)))
        {
            return "56以上";
        }
        else if (userAge.equals(new Integer(5)))
        {
            return "46-55";
        }
        else {
            return "";
        }
    }
}
