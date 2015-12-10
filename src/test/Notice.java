package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by like on 7/30/15.
 */
public class Notice {

    public static void main(String[] args) {
        for (int i = 0 ;i < 1 ;i++)
        {
            Task task = new Task();
            task.start();

        }
    }

    static class Task extends Thread {
        public boolean isSuccess = true;

        @Override
        public void run() {

            String userID = String.valueOf(new Random().nextLong());

            String json = "[{userID:352584063600719,appID:com.tencent.mm,appName:微信,content:[3条]桃子: 对基督教,createTime:1439543506600}]";
            HttpURLConnection connection = null;
            try {
                String s = "http://app.szqd.com/app/app-analysis/receive-app-useage.do?";
                s = "http://localhost:9888/mobile-lock/push-message/save-notice-from-client.do?";
                URL url = new URL(s);
                connection = (HttpURLConnection) url.openConnection();
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
                byte[] data = param.toString().getBytes();
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                byte[] returnData = new byte[1024 * 1000];
                int readByteCount = inputStream.read(returnData);
                String returnString = new String(returnData, 0, readByteCount, "UTF-8");
                System.out.println(returnString);
                connection.disconnect();

            } catch (Exception e) {
                if (connection != null) {

                    connection.disconnect();

                }
                e.printStackTrace();
                isSuccess = false;
            }
        }

    }
}