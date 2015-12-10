package com.szqd.project.common.model;

import org.springframework.data.annotation.Transient;

/**
 * Created by like on 5/20/15.
 */
public class AdvertisingEntityPojo extends AdvertisingEntityDB
{
    public String getTypeText()
    {
        Integer type = this.getType();

        Integer startUpID = AdvertisingTypeEnum.START_UP.getId();
        if (startUpID.equals(type))
        {
            return AdvertisingTypeEnum.START_UP.getName();
        }

        Integer welcomePageID = AdvertisingTypeEnum.WELCOME_PAGE.getId();
        if (welcomePageID.equals(type))
        {
            return AdvertisingTypeEnum.WELCOME_PAGE.getName();
        }

        return "";

    }

    @Transient
    private Long projectID;

    @java.beans.Transient
    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    
}
