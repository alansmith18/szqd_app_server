package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum RecommendFlagEnum {

    HOT(1,"热门"),QUALITY(2,"优质");

    private Integer id = null;
    private String name = null;

    RecommendFlagEnum(Integer id, String name) {
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
