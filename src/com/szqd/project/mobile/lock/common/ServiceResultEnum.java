package com.szqd.project.mobile.lock.common;

/**
 * Created by mac on 14-5-28.
 */
public enum ServiceResultEnum {
    CALL_ERROR(0),CALL_DATA(1),ERROR_INFO(2);

    private int code;

    ServiceResultEnum(int code) {
        this.code = code;
    }


    public static final int ERROR_CODE_SQL_ERROR = 1;
    public static final int ERROR_CODE_ERROR = 2;
}
