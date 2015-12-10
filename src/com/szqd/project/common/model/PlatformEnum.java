package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum PlatformEnum {
    ANDROID(1,"ANDROID"),IOS(2,"IOS"),WINMOBILE(3,"WINMOBILE"),OTHER(4,"OTHER");

    private Integer id = null;
    private String name = null;

    PlatformEnum(Integer id, String name) {
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
