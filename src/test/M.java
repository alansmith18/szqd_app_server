package test;

import com.szqd.framework.model.Resolution;
import com.szqd.framework.util.ImageUtil;
import com.szqd.framework.util.URLConnectionUtils;
import com.szqd.framework.util.URLConnectionUtilsParam;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * Created by like on 4/27/15.
 */
public class M {
    public static void main(String[] args) throws Exception {
        URLConnectionUtilsParam param = new URLConnectionUtilsParam();
        param.encoding = "UTF-8";
        param.urlAddr = "https://127.0.0.1:9888/app/login.jsp";
        param.method = "GET";
        param.isSSLAuth = false;
        String result = URLConnectionUtils.send(param);
        System.out.println(result);
//        URL url = new URL("hTTPS://www.baidu.com");
//        System.out.println(url.getProtocol());
    }




}