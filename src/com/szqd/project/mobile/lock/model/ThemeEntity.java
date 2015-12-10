package com.szqd.project.mobile.lock.model;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by like on 5/8/15.
 */
public class ThemeEntity extends ThemeEntityDB {

    private static final Logger LOGGER = Logger.getLogger(ThemeEntity.class);
    public String getCreateTimeText()
    {
        Long createTime = this.getCreateTime();
        if (createTime != null)
        {
            try {
                Date date = new Date(createTime);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
                return sf.format(date);
            } catch (Exception e) {
                LOGGER.error("com.szqd.project.mobile.lock.model.ThemeEntity.getCreateTimeText()",e);
            }
        }
        return null;
    }
}
