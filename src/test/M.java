package test;

import com.szqd.framework.model.Resolution;
import com.szqd.framework.util.ImageUtil;
import com.szqd.framework.util.URLConnectionUtils;
import com.szqd.framework.util.URLConnectionUtilsParam;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by like on 4/27/15.
 */
public class M {
    public static void main(String[] args) throws Exception {
        URLConnectionUtilsParam param = new URLConnectionUtilsParam();
        param.encoding = "UTF-8";
        param.urlAddr = "https://127.0.0.1:9888/app/login.jsp";
        param.method = "GET";
        param.isSSLAuth = true;
        String result = URLConnectionUtils.send(param);
        System.out.println(result);

//        StringBuffer sb = new StringBuffer();
//        Provider[] p = Security.getProviders();
//        for (int i = 0; i < p.length; i++) {
//            sb.append("\nProvider : " + p[i].toString() + "\n");
//            Set s = p[i].keySet();
//            Object[] o = s.toArray();
//            Arrays.sort(o);
//            for (int j = 1; j < o.length; j++) {
//                sb.append(o[j].toString() + ", ");
//            }
//        }
//        System.out.println(sb.toString());
    }




}