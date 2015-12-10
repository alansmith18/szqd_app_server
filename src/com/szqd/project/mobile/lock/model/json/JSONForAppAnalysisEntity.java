package com.szqd.project.mobile.lock.model.json;


import java.util.List;

/**
 * Created by like on 3/27/15.
 */
public class JSONForAppAnalysisEntity
{


    private String deviceToken = null;

    private List<RecordListEntity> recordList = null;

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public List<RecordListEntity> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<RecordListEntity> recordList) {
        this.recordList = recordList;
    }
}


