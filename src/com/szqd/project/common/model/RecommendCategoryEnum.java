package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum RecommendCategoryEnum {
    RECOMMEND(1,"精品推荐"),MAIN_INTERFACE(2,"主界面推荐位");

    private Integer id = null;
    private String name = null;

    RecommendCategoryEnum(Integer id, String name) {
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
