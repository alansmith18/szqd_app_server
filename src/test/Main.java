package test;


import com.szqd.framework.util.URLConnectionUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by like on 15/3/13.
 */
public class Main {
    public static void main(String[] args) throws Exception
    {
        String URL_SPORTS_NEWS = "http://2345api.dfshurufa.com/top/shoudiantong";
        String newsJson = URLConnectionUtils.send(URL_SPORTS_NEWS, null, "GET", "UTF-8");
        System.out.println(newsJson);
    }
}

class Task extends Thread
{
    public static boolean isSuccess = true;
    @Override
    public void run() {
        String json = "{\n" +
                "\n" +
                "\"deviceToken\":\"353918055122994\",\n" +
                "\"recordList\":\n" +
                "[\n" +
                "{\n" +
                "\"appName\":\"神指日历\"\n" +
                ",\"exitTime\":\"2015-03-26 12:58:48\"\n" +
                ",\"packageName\":\"com.szqd.calendar\"\n" +
                ",\"lauchTime\":\"2015-03-26 12:57:48\"\n" +
                "},\n" +
                "{\n" +
                "\"appName\":\"闪击\"\n" +
                ",\"exitTime\":\"2015-03-26 12:58:49\"\n" +
                ",\"packageName\":\"com.szqd.shanji\"\n" +
                ",\"lauchTime\":\"2015-03-26 12:57:49\"\n" +
                "},\n" +
                "{\n" +
                "\"appName\":\"UC浏览器\"\n" +
                ",\"exitTime\":\"2015-03-26 12:58:50\"\n" +
                ",\"packageName\":\"com.uc.browser\"\n" +
                ",\"lauchTime\":\"2015-03-26 12:57:50\"\n" +
                "}\n" +
                "]\n" +
                "\n" +
                "}";
        String userID = String.valueOf(new Random().nextLong());
        userID = "8921656674286428692";
        json = "{\"deviceToken\":\""+userID+"\",\"recordList\":[{\"appName\":\"神指日历\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 11:11:36\",\"packageName\":\"com.szqd.calendar\",\"lauchTime\":\"2015-04-15 07:21:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"闪击\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.szqd.shanji\",\"lauchTime\":\"2015-04-15 19:11:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"UC浏览器\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.uc.browser\",\"lauchTime\":\"2015-04-15 10:11:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"QQ\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.tencent.qq\",\"lauchTime\":\"2015-04-15 10:11:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"微信\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.tencent.weixin\",\"lauchTime\":\"2015-04-15 10:11:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"万年历\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.wnl.calendar\",\"lauchTime\":\"2015-04-15 10:11:36\",\"isUploaded\":0,\"id\":0},{\"appName\":\"豌豆荚\",\"createTime\":\"2015-04-15 10:11:36\",\"exitTime\":\"2015-04-15 10:11:36\",\"packageName\":\"com.wandoujia.helloworld\",\"lauchTime\":\"2015-04-15 10:11:36\",\"isUploaded\":0,\"id\":0}]}";
        HttpURLConnection connection = null;
        try
        {
            String s = "http://app.szqd.com/app/app-analysis/receive-app-useage.do?";
            s = "http://localhost:9888/mobile-lock/app-analysis/receive-app-useage.do?";
            URL url = new URL(s);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDefaultUseCaches(false);
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            StringBuilder param = new StringBuilder();
            param.append("json=");
            param.append(json);
            System.out.println(param.toString());
            byte[]data = param.toString().getBytes();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            byte[] returnData = new byte[1024*1000];
            int readByteCount = inputStream.read(returnData);
            String returnString = new String(returnData,0,readByteCount,"UTF-8");
            System.out.println(returnString);
            connection.disconnect();

        } catch (Exception e)
        {
            if (connection != null) {

                connection.disconnect();

            }
            e.printStackTrace();
            isSuccess = false;
        }
    }
}
