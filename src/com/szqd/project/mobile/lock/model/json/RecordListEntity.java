package com.szqd.project.mobile.lock.model.json;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordListEntity
{
    private static final Logger logger = Logger.getLogger(RecordListEntity.class);

    private String appName = null;

    private String exitTime = null;

    private String packageName = null;

    private String lauchTime = null;

    public Long getLauchTimeForLongType()
    {
        return getTimeForLongTypeWithInputDateString(this.lauchTime);
    }

    public Long getExitTimeForLongType()
    {
        return getTimeForLongTypeWithInputDateString(this.exitTime);
    }


    private static final String ERROR_FOR_DATE_FORMAT = "格式化日期:%s 出错,正确的格式应该为:yyyy-MM-dd HH:mm:ss";
    private Long getTimeForLongTypeWithInputDateString(String dateString)
    {

        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sf.parse(dateString);
            return date.getTime();
        } catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.model.json.RecordListEntity.getTimeForLongTypeWithInputDateString(String dateString)", e);
            String errorInfo = String.format(ERROR_FOR_DATE_FORMAT, dateString);
            throw new RuntimeException(errorInfo);
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLauchTime() {
        return lauchTime;
    }

    public void setLauchTime(String lauchTime) {
        this.lauchTime = lauchTime;
    }
}
