package com.szqd.project.common.model.push;

/**
 * Created by like on 8/24/15.
 */
public class ClickRecordDB
{
    public static final String ENTITY_NAME = "COMMON_CLICK_RECORD";

    private Integer id;
    private Integer clickCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}
