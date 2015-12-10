package com.szqd.project.common.model.push;

/**
 * Created by like on 7/8/15.
 */
public enum TargetURLTypeEnum {

    FILE_TYPE(1,"文件"), WEBSITE_TYPE(2,"网页");

    private Integer id = null;
    private String name = null;

    TargetURLTypeEnum(Integer id, String name) {
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
