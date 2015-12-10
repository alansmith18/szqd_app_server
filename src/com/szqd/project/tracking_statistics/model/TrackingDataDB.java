package com.szqd.project.tracking_statistics.model;

/**
 * Created by like on 11/13/15.
 */
public class TrackingDataDB {

    public static final String  ENTITY_NAME = "TRACKING_STATISTICS_TRACKING_DATA";

    private Object id;

    private Long eventID = null;

    private Long ip = null;

    private Long createDay = null;

    private Long createTime = null;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getIp() {
        return ip;
    }

    public void setIp(Long ip) {
        this.ip = ip;
    }

    public Long getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Long createDay) {
        this.createDay = createDay;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
