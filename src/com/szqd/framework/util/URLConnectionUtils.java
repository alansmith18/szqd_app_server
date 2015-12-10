package com.szqd.framework.util;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by like on 9/18/15.
 */
public class URLConnectionUtils {

    private static final Logger LOGGER = Logger.getLogger(URLConnectionUtils.class);

    private static String bindParam(Map<String,String> params,String encoding)throws Exception
    {
        StringBuilder paramString = new StringBuilder();
        if (params != null) {
            Set<Map.Entry<String,String>> entrySet = params.entrySet();
            int i = 1;
            for (Map.Entry<String, String> param : entrySet)
            {
                String paramName = param.getKey();
                String paramValue = param.getValue();
                paramValue = URLEncoder.encode(paramValue,encoding);
                paramString.append(paramName).append("=").append(paramValue);
                if (i < entrySet.size()) {
                    paramString.append("&");
                }
                i++;
            }
        }

        LOGGER.debug("请求参数为:" + paramString.toString());
        return paramString.toString();
    }

    private static void writeParam(HttpURLConnection connection,String paramString,String encoding)throws Exception
    {
        byte[]data = paramString.toString().getBytes(encoding);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    private static String sendGet(String urlAddr,Map<String,String> params,String encoding)throws Exception
    {
        HttpURLConnection connection = null;
        try {
            StringBuilder urlForParam = new StringBuilder().append(urlAddr);
            if (params != null && !params.isEmpty())
            {
                String paramString = bindParam(params,encoding);
                urlForParam = urlForParam.append("?").append(paramString);
            }

            connection = configConnection(urlForParam.toString(),"GET");
            return responseData(connection, encoding);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String sendPOST(String urlAddr,Map<String,String> params,String encoding)throws Exception
    {
        HttpURLConnection connection = null;
        try {
            connection = configConnection(urlAddr, "POST");
            String paramString = bindParam(params,encoding);
            writeParam(connection, paramString, encoding);
            return responseData(connection, encoding);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static HttpURLConnection configConnection(String url,String method)throws Exception
    {
        HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setDefaultUseCaches(false);
        connection.setReadTimeout(200000);
        connection.setConnectTimeout(200000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();
        return connection;
    }

    private static String responseData(HttpURLConnection connection,String encoding)throws Exception
    {

        LOGGER.debug(connection.getResponseCode());
        LOGGER.debug(connection.getResponseMessage());
        InputStream inputStream = connection.getInputStream();
        byte[] bufferData = new byte[1024*3];
        int readByteCount;
        byte[] totalData = new byte[0];
        do {
            readByteCount = inputStream.read(bufferData,0,bufferData.length);
            if (readByteCount != -1)
            {
                int lastEnd = totalData.length;
                int totalDataCount = lastEnd + readByteCount;
                byte[] tempData = new byte[totalDataCount];

                if (lastEnd != 0) System.arraycopy(totalData,0,tempData,0,totalData.length);

                System.arraycopy(bufferData,0,tempData,lastEnd,readByteCount);

                totalData = tempData;

            }
        } while (readByteCount != -1);
        return new String(totalData,"UTF-8");
    }

    public static String send(String urlAddr,Map<String,String> params,String method,String encoding)throws Exception
    {
        String responseData = null;
        if ("GET".equalsIgnoreCase(method)) {
            responseData = sendGet(urlAddr,params,encoding);
        }
        else if ("POST".equalsIgnoreCase(method))
        {
            responseData = sendPOST(urlAddr,params,encoding);
        }
        LOGGER.debug("返回的内容为:"+responseData);
        return responseData;
    }

    public static void sendAsync(String urlAddr,Map<String,String> params,String method,String encoding)throws Exception
    {
        new Thread(new SendThread(urlAddr,params,method,encoding)).start();
    }



}


class SendThread implements Runnable
{
    private String urlAddr;
    private Map<String,String> params;
    private String method;
    private String encoding;

    public SendThread(String urlAddr, Map<String, String> params, String method, String encoding) {
        this.urlAddr = urlAddr;
        this.params = params;
        this.method = method;
        this.encoding = encoding;
    }

    @Override
    public void run(){
        try {
            URLConnectionUtils.send(urlAddr,params,method,encoding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}