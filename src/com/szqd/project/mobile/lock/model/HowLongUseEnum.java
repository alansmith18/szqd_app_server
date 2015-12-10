package com.szqd.project.mobile.lock.model;

/**
 * Created by like on 5/29/15.
 */
public enum  HowLongUseEnum {
    S1$S3(1,"1-3 秒"),S4$S10(2,"4-10 秒"),S11$S30(3,"11-30 秒"),S31$S60(4,"31-60 秒")
    ,M1$M3(5,"1-3 分"),M3$M10(6,"3-10 分"),M10$M30(7,"10-30 分"),M30OVER(8,"30分以上");

    private Integer id;
    private String name;

    HowLongUseEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HowLongUseEnum getEnumByID(int id)
    {
        switch (id)
        {
            case 1:
            {
                return S1$S3;
            }
            case 2:
            {

                return S4$S10;
            }
            case 3:
            {

                return S11$S30;
            }
            case 4:
            {

                return S31$S60;
            }
            case 5:
            {

                return M1$M3;
            }
            case 6:
            {

                return M3$M10;
            }
            case 7:
            {

                return M10$M30;
            }
            case 8:
            {

                return M30OVER;
            }
        }
        return null;
    }
}
