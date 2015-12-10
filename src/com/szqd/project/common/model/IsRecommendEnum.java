package com.szqd.project.common.model;

/**
 * Created by like on 5/18/15.
 */
public enum IsRecommendEnum {
    RECOMMEND(1,"推荐"),NO_RECOMMEND(2,"不推荐");

    private Integer id = null;
    private String name = null;

    IsRecommendEnum(Integer id, String name) {
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
