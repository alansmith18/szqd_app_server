package com.szqd.project.tracking_statistics.model;

/**
 * Created by like on 11/18/15.
 */
public class TrackingEventPOJO extends TrackingEventDB
{
    private String nameRegex = null;

    public String getNameRegex() {
        return nameRegex;
    }

    public void setNameRegex(String nameRegex) {
        this.nameRegex = nameRegex;
    }
}
