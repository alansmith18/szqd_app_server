package com.szqd.project.common.model.push;

import java.util.List;

/**
 * Created by like on 7/15/15.
 */
public class PushRecord {

    public static final String ENTITY_NAME = "COMMON_PUSH_RECORD";



    private Integer pushID;

    private Integer pushCount;

    private Long createTime;


    public Integer getPushID() {
        return pushID;
    }

    public void setPushID(Integer pushID) {
        this.pushID = pushID;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
