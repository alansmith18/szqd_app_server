package test;

import com.szqd.project.popularize.analysis.model.AppActivationEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by like on 4/3/15.
 */
public class PostMain
{

    public static void main(String[] args)
    {

        List<AppActivationEntity> jsonList = new ArrayList<AppActivationEntity>();

        AppActivationEntity e = new AppActivationEntity();
        e.setAppName("闪记");
        e.setAppID("com.szqd.shanji");
        e.setDeviceID("666918055122994");
        e.setPlatformID(1);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("闪记");
        e.setAppID("com.szqd.shanji");
        e.setDeviceID("666918055122994");
        e.setPlatformID(2);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("闪记");
        e.setAppID("com.szqd.shanji");
        e.setDeviceID("777918055122994");
        e.setPlatformID(1);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("闪记");
        e.setAppID("com.szqd.shanji");
        e.setDeviceID("777918055122994");
        e.setPlatformID(2);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        ////

        e = new AppActivationEntity();
        e.setAppName("手电筒");
        e.setAppID("com.szqd.shoudiantong");
        e.setDeviceID("666918055122994");
        e.setPlatformID(3);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("手电筒");
        e.setAppID("com.szqd.shoudiantong");
        e.setDeviceID("666918055122994");
        e.setPlatformID(2);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("手电筒");
        e.setAppID("com.szqd.shoudiantong");
        e.setDeviceID("777918055122994");
        e.setPlatformID(1);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        e = new AppActivationEntity();
        e.setAppName("手电筒");
        e.setAppID("com.szqd.shoudiantong");
        e.setDeviceID("777918055122994");
        e.setPlatformID(3);
        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
        jsonList.add(e);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"userCount", "createTimeText"});
        for (int i = 0 ; i < jsonList.size() ; i ++)
        {
            AppActivationEntity appActivationEntity = jsonList.get(i);
            String json = JSONObject.fromObject(appActivationEntity,jsonConfig).toString();
//            System.out.println(json);
            try
            {
                URL url = new URL("http://127.0.0.1:8088/mobile-lock/popularize/add-activation.do?");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
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

//            System.out.println(param.toString());
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

            } catch (Exception e1)
            {
                e1.printStackTrace();
            }
        }




    }
}
