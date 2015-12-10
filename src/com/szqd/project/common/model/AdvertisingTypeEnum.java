package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum AdvertisingTypeEnum {

    START_UP(1,"启动画面"),WELCOME_PAGE(2,"欢迎页面");

    private Integer id = null;
    private String name = null;

    AdvertisingTypeEnum(Integer id, String name) {
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
