package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum RecommendOtherFlagEnum {

    COOPERATION_360(1,"360合作"),OTHER(2,"其他");

    private Integer id = null;
    private String name = null;

    RecommendOtherFlagEnum(Integer id, String name) {
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
