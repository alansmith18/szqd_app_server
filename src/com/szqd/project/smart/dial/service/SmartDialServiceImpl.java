package com.szqd.project.smart.dial.service;

import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.SecurityUtil;
import com.szqd.framework.util.URLConnectionUtils;
import com.szqd.framework.util.URLConnectionUtilsParam;
import com.szqd.project.smart.dial.model.SmartDialUserDownloadDB;
import com.szqd.project.smart.dial.model.SmartDialUserVisitAppStoreDB;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by like on 9/18/15.
 */
public class SmartDialServiceImpl extends SuperService implements SmartDialService
{
    private static final Logger LOGGER = Logger.getLogger(SmartDialServiceImpl.class);
    private static final String APPKEY_FOR_SMART_DIAL = "962585252";
    private static final String SIGN_KEY_OF_DOMOB = "9e01d6ad8e8b27a175e64d4dd74881bc";

    /**
     * 记录神指拨号的商店访问
     * @param deviceID
     */
    public void recordSmartDialVisitStore(String appkey,String deviceID)
    {
        if (APPKEY_FOR_SMART_DIAL.equals(appkey))
        {
            try
            {
                SmartDialUserVisitAppStoreDB smartDial = new SmartDialUserVisitAppStoreDB();
                smartDial.setCreateTime(new Date().getTime());
                smartDial.setDeviceID(deviceID);
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                mongoTemplate.insert(smartDial,SmartDialUserVisitAppStoreDB.ENTITY_NAME);
            }
            catch (Exception e) {
                LOGGER.error("com.szqd.project.smart.dial.service.SmartDialServiceImpl.addSmartDialVisitRecord(String deviceID)",e);
                throw new RuntimeException("记录神指拨号的商店访问出错");
            }
        }
    }



    /**
     * 记录神指拨号的下载
     * @param appkey
     * @param mac
     * @param ifa
     */
    public void recordSmartDialDownload(String appkey,String mac,String ifa)
    {
        if (APPKEY_FOR_SMART_DIAL.equals(appkey))
        {
            try {
                String macmd5 = SecurityUtil.md5(mac).toUpperCase();
                String ifamd5 = SecurityUtil.md5(ifa);
                SmartDialUserDownloadDB smartDial = new SmartDialUserDownloadDB();
                smartDial.setCreateTime(new Date().getTime());
                smartDial.setDeviceID(ifa);
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                mongoTemplate.insert(smartDial,SmartDialUserDownloadDB.ENTITY_NAME);

                this.sendSmartDialDownloadToDomob(mac, macmd5, ifa, ifamd5);



            } catch (Exception e) {
                LOGGER.error("com.szqd.project.smart.dial.service.SmartDialServiceImpl.recordSmartDialDownload(String deviceID)", e);
                throw new RuntimeException("记录神指拨号的下载出错");
            }
        }
    }

    /**
     * 发送多盟回调
     * @param mac
     * @param macmd5
     * @param ifa
     * @param ifamd5
     */
    private void sendSmartDialDownloadToDomob(String mac,String macmd5,String ifa,String ifamd5)
    {

        String signOfDomob = getDomobSign(APPKEY_FOR_SMART_DIAL,mac,macmd5,ifa,ifamd5,SIGN_KEY_OF_DOMOB);
        String addr = "http://e.domob.cn/track/ow/api/callback";
        Map<String,String> param = new HashMap<>();
        param.put("appkey",APPKEY_FOR_SMART_DIAL);
        param.put("acttype","2");
        param.put("mac",mac);
        param.put("macmd5",macmd5);
        param.put("ifa",ifa);
        param.put("ifamd5",ifamd5);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.MILLISECOND,0);
        param.put("acttime",String.valueOf(now.getTimeInMillis()/1000));
        param.put("sign",signOfDomob);
        try {
            URLConnectionUtilsParam paramForRequest = new URLConnectionUtilsParam();
            paramForRequest.encoding = "UTF-8";
            paramForRequest.urlAddr = addr;
            paramForRequest.params = param;
            paramForRequest.method = "GET";
            URLConnectionUtils.send(paramForRequest);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.smart.dial.service.SmartDialServiceImpl.sendSmartDialDownloadToDomob(String mac,String macmd5,String ifa,String ifamd5)",e);
            throw new RuntimeException("发送多盟回调出错");
        }
    }

    /**
     * 获取多盟签名
     * @param appkey
     * @param mac
     * @param macmd5
     * @param ifa
     * @param ifamd5
     * @param signKey
     * @return
     * @throws Exception
     */
    private static String getDomobSign(String appkey, String mac, String macmd5, String ifa,  String ifamd5, String signKey) {
        char[] str = new char[0];
        try {
            char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f' };
            String s = appkey + "," + mac + "," + macmd5 + "," + ifa +  "," + ifamd5 + "," + signKey;
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = HEX_DIGITS[(byte0 >>> 4 & 0xF)];
                str[(k++)] = HEX_DIGITS[(byte0 & 0xF)];
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("com.szqd.project.smart.dial.service.SmartDialServiceImpl.getDomobSign(String appkey, String mac, String macmd5, String ifa,  String ifamd5, String signKey)",e);
            throw new RuntimeException("获取多盟签名出错");
        }
        return new String(str);
    }



}
