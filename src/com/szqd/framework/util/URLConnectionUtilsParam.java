package com.szqd.framework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by like on 12/15/15.
 */
public class URLConnectionUtilsParam {
    public String urlAddr = null;
    public Map<String, String> params = new HashMap<>();
    public String method ="GET";
    public String encoding = "UTF-8";
    public boolean isSSLAuth = false;
}
