package com.szqd.framework.util;

import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

/**
 * Created by like on 9/18/15.
 */
public class URLConnectionUtils {

    private static final Logger LOGGER = Logger.getLogger(URLConnectionUtils.class);

    private static String bindParam(Map<String, String> params, String encoding) throws Exception {
        StringBuilder paramString = new StringBuilder();
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            int i = 1;
            for (Map.Entry<String, String> param : entrySet) {
                String paramName = param.getKey();
                String paramValue = param.getValue();
                paramValue = URLEncoder.encode(paramValue, encoding);
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

    private static void writeParam(URLConnection connection, String paramString, String encoding) throws Exception {
        byte[] data = paramString.toString().getBytes(encoding);
        try(OutputStream outputStream = connection.getOutputStream();)
        {
            outputStream.write(data);
            outputStream.flush();
        }
    }

    private static String sendGet(URLConnectionUtilsParam param) throws Exception {

        StringBuilder urlForParam = new StringBuilder().append(param.urlAddr);
        if (param.params != null && !param.params.isEmpty()) {
            String paramString = bindParam(param.params, param.encoding);
            urlForParam = urlForParam.append("?").append(paramString);
        }
        URLConnection connection = configConnection(urlForParam.toString(), "GET",param.isSSLAuth);
        return responseData(connection, param.encoding);
    }

    private static String sendPOST(URLConnectionUtilsParam param) throws Exception {
        URLConnection connection = null;
        connection = configConnection(param.urlAddr, "POST",param.isSSLAuth);
        String paramString = bindParam(param.params, param.encoding);
        writeParam(connection, paramString, param.encoding);
        return responseData(connection, param.encoding);
    }

    private static URLConnection configConnection(String url, String method, boolean isSSLAuth) throws Exception {

        URL urlObj = new URL(url);
        URLConnection connection =  urlObj.openConnection();
        connection.setDefaultUseCaches(false);
        connection.setReadTimeout(200000);
        connection.setConnectTimeout(200000);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        String protocol = urlObj.getProtocol();
        if ("http".equals(protocol))
        {
            HttpURLConnection httpCon = (HttpURLConnection)connection;
            httpCon.setRequestMethod(method);
        }
        else{
            HttpsURLConnection httpsCon = (HttpsURLConnection)connection;
            httpsCon.setRequestMethod(method);
            SSLContext sslContext = null;
            if (isSSLAuth) {
                sslContext = getAuthSSLContext();
            }
            else{
                sslContext = getSSLContext();
            }

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            httpsCon.setHostnameVerifier(allHostsValid);

            httpsCon.setSSLSocketFactory(sslContext.getSocketFactory());
        }

        connection.connect();
        return connection;
    }



    private static String responseData(URLConnection connection, String encoding) throws Exception
    {
//        LOGGER.debug(connection.getResponseCode());
//        LOGGER.debug(connection.getResponseMessage());
        try(InputStream inputStream = connection.getInputStream();)
        {
            byte[] bufferData = new byte[1024 * 3];
            int readByteCount;
            byte[] totalData = new byte[0];
            do {
                readByteCount = inputStream.read(bufferData, 0, bufferData.length);
                if (readByteCount != -1) {
                    int lastEnd = totalData.length;
                    int totalDataCount = lastEnd + readByteCount;
                    byte[] tempData = new byte[totalDataCount];

                    if (lastEnd != 0) System.arraycopy(totalData, 0, tempData, 0, totalData.length);

                    System.arraycopy(bufferData, 0, tempData, lastEnd, readByteCount);

                    totalData = tempData;

                }
            } while (readByteCount != -1);
            return new String(totalData, "UTF-8");
        }

    }

    public static String send(URLConnectionUtilsParam param) throws Exception {
        String responseData = null;
        if ("GET".equalsIgnoreCase(param.method)) {
            responseData = sendGet(param);
        } else if ("POST".equalsIgnoreCase(param.method)) {
            responseData = sendPOST(param);
        }
        LOGGER.debug("返回的内容为:" + responseData);
        return responseData;
    }

    private static SSLContext getSSLContext()throws Exception
    {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };
        sslContext.init(null, trustAllCerts, new SecureRandom());



        return sslContext;
    }

    private static class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static final String KEY_STORE_TYPE_BKS = "JKS";//证书类型 固定值
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型 固定值
    private static final String KEY_STORE_CLIENT_PATH = "com/szqd/framework/security/ssl/client.p12";//客户端要给服务器端认证的证书
    private static final String KEY_STORE_TRUST_PATH = "com/szqd/framework/security/ssl/client.truststore";//客户端验证服务器端的证书库
    private static final String KEY_STORE_PASSWORD = "like1982";// 客户端证书密码
    private static final String KEY_STORE_TRUST_PASSWORD = "like1982";//客户端证书库密码
    /**
     * 获取需要验证的SSLContext
     *
     * @return SSLContext
     */
    private static SSLContext getAuthSSLContext() throws Exception {

//        // 服务器端需要验证的客户端证书
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
//        // 客户端信任的服务器端证书
        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try
        (
            InputStream keyStoreClientInput = classLoader.getResource(KEY_STORE_CLIENT_PATH).openStream();
            InputStream trustStoreClientInput = classLoader.getResource(KEY_STORE_TRUST_PATH).openStream();
        )
        {
            keyStore.load(keyStoreClientInput, KEY_STORE_PASSWORD.toCharArray());
            trustStore.load(trustStoreClientInput, KEY_STORE_TRUST_PASSWORD.toCharArray());
        }
        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }


//    public static void sendAsync(String urlAddr, Map<String, String> params, String method, String encoding) throws Exception {
//        new Thread(new SendThread(urlAddr, params, method, encoding)).start();
//    }


}


//class SendThread implements Runnable {
//    private String urlAddr;
//    private Map<String, String> params;
//    private String method;
//    private String encoding;
//
//    public SendThread(String urlAddr, Map<String, String> params, String method, String encoding) {
//        this.urlAddr = urlAddr;
//        this.params = params;
//        this.method = method;
//        this.encoding = encoding;
//    }
//
//    @Override
//    public void run() {
//        try {
//            URLConnectionUtils.send(urlAddr, params, method, encoding);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}