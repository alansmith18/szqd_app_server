package com.szqd.framework.controller;

/**
 * Created by mac on 14-5-28.
 */
public enum PageGlobalKeyEnum {


    STATUS("status")
    ,
    STATUS_ERROR_CODE("500")
    ,
    STATUS_SUCCESS_CODE("200")
    ,
    RESULT_DATA("resultData")
    ,
    ERROR_INFO("errorInfo");

    private String key;

    private PageGlobalKeyEnum(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
