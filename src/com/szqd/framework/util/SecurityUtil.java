package com.szqd.framework.util;

import java.security.MessageDigest;

/**
 * Created by like on 9/21/15.
 */
public class SecurityUtil {
    public static String md5(String str)throws Exception
    {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] cipherData = md5.digest(str.getBytes());
        StringBuilder builder = new StringBuilder();
        for(byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }
        return builder.toString();
    }

}
