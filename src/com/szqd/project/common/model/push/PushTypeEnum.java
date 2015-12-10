package com.szqd.project.common.model.push;

/**
 * Created by like on 7/8/15.
 */
public enum PushTypeEnum {

    STANDARD(1,"标准"),DETAIL(2,"详细");

    private Integer id = null;
    private String name = null;

    PushTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }


    public static String getNameByID(Integer id)
    {
        if (STANDARD.getId().equals(id))
        {
            return STANDARD.getName();
        }
        else if (DETAIL.getId().equals(id))
        {
            return DETAIL.getName();
        }
        return "";
    }
}
