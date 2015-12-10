package com.szqd.project.common.model;

/**
 * Created by like on 5/12/15.
 */
public enum NetOperatorsEnum {
    NET2G(1,"2G"),NET3G(2,"3G"),NET4G(3,"4G"),NETWIFI(4,"WIFI");

    private Integer id = null;
    private String name = null;

    NetOperatorsEnum(Integer id, String name) {
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
