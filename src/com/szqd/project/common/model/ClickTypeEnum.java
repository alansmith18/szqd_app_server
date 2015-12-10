package com.szqd.project.common.model;

/**
 * Created by like on 9/14/15.
 */
public enum ClickTypeEnum {
    DOWNLOAD_FILE(1,"下载文件"),WEBSITE(2,"访问网页");

    private Integer id = null;
    private String name = null;

    ClickTypeEnum(Integer id, String name) {
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
