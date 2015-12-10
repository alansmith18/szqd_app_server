package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum ProjectStatusEnum {
    ENABLE(1,"启用"),DISABLE(0,"不启用");

    private Integer id = null;
    private String name = null;

    ProjectStatusEnum(Integer id, String name) {
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
