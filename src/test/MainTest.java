package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by like on 7/9/15.
 */
public class MainTest {
    public static void main(String[] args) throws Exception {

//        String path = Thread.currentThread().getContextClassLoader().getResource("com/szqd/framework/config/log4j.properties").getPath();
//        Log4jConfigurer.initLogging(path);
//        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");
//        PushMessageService pushMessageService = (PushMessageService)context.getBean("pushMessageService");
//


            Task1 t = new Task1();
            t.start();



    }
}

class Task1 extends Thread
{
    public static boolean isSuccess = true;
    @Override
    public void run() {

        String json = "[{\"appName\":\"QQ音乐\",\"launchTimes\":5,\"useTime\":662817},{\"appName\":\"360手机助手\",\"launchTimes\":3,\"useTime\":28002},{\"appName\":\"Root Explorer\",\"launchTimes\":1,\"useTime\":60013},{\"appName\":\" 360卫士\",\"launchTimes\":1,\"useTime\":2003},{\"appName\":\"优酷\",\"launchTimes\":5,\"useTime\":229115}]";
        HttpURLConnection connection = null;
        try
        {
            String s = "http://app.szqd.com/app/push-message/send-push-message-to-client.do?";
            s = "http://localhost:9888/app/push-message/send-push-message-to-client.do?";
            URL url = new URL(s);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDefaultUseCaches(false);
            connection.setReadTimeout(200000);

            connection.setConnectTimeout(200000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            StringBuilder param = new StringBuilder();
            param.append("projectID=");
            param.append(2).append("&");
            param.append("userID=").append("-1").append("&");
            param.append("interestJson=");
            param.append(json);
            System.out.println(param.toString());
            byte[]data = param.toString().getBytes();
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();

            InputStream inputStream = connection.getInputStream();
            byte[] returnData = new byte[1024*1000*10];
            int readByteCount;
            StringBuilder sb = new StringBuilder();
            do {
                readByteCount = inputStream.read(returnData,0,returnData.length);
                if (readByteCount != -1) {
                    String returnString = new String(returnData,0,readByteCount,"UTF-8");
                    sb.append(returnString);
                }
            } while (readByteCount != -1);

            connection.disconnect();

            System.out.println(sb.toString());

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
