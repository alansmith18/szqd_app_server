package com.szqd.project.common.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by like on 5/18/15.
 */
public class SkinEntityPojo extends SkinEntityDB
{
    public String getCreateTimeText()
    {
        String s = null;
        try {
            Long createTime = this.getCreateTime();
            Date date = new Date(createTime);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            s = sf.format(date);
        } catch (Exception e) {

        }
        return s;
    }

    public String getModifyTimeText()
    {
        String s = null;
        try {
            Long modifyTime = this.getModifyTime();
            Date date = new Date(modifyTime);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            s = sf.format(date);
        } catch (Exception e) {

        }
        return s;
    }

    public String getStatusText()
    {
        String s = null;
        Integer status = this.getStatus();
        if (ProjectStatusEnum.ENABLE.getId().equals(status))
        {
            return ProjectStatusEnum.ENABLE.getName();
        }
        else if (ProjectStatusEnum.DISABLE.getId().equals(status))
        {
            return ProjectStatusEnum.DISABLE.getName();
        }

        return null;
    }
}
