package com.szqd.framework.util;

/**
 * Created by like on 11/13/15.
 */
public class StringUtil {


    public static boolean isValidEmail(String email)
    {
//        email.matches()
//        if ("""(?=[^\s]+)(?=(\w+)@([\w\.]+))""".r.findFirstIn(email) == None)
//        return false;
//        else
//        return true;
        return false;
    }


    public static boolean isValidPhone(String phone)
    {
        if (phone.matches("^\\d{11}$"))
            return true;
        else
            return false;
    }

    public static long ipToLong(String strIp)
    {

        long[] ip = new long[4];
        //先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        //将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3+1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }


}
