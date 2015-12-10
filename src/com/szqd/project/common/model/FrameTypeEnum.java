package com.szqd.project.common.model;

/**
 * Created by like on 9/14/15.
 */
public enum FrameTypeEnum {
    FULL_SCREEN(1,"全屏"),TWO_THIRDS_SCREEN(2,"三分之二屏");

    private Integer id = null;
    private String name = null;

    FrameTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }
}
