package test;

import com.szqd.framework.security.UsersRole;
import com.szqd.project.popularize.analysis.model.AppActivationEntity;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import com.szqd.project.popularize.analysis.service.PopularizeAnalysisService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by like on 3/31/15.
 */
public class PopularizeMain {

    public static void main(String[] args)throws Exception
    {
//        String path = Thread.currentThread().getContextClassLoader().getResource("com/szqd/framework/config/log4j.properties").getPath();
//        Log4jConfigurer.initLogging(path);
//        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");
//        PopularizeAnalysisService service = (PopularizeAnalysisService)context.getBean("popularizeAnalysisService");

//        AppActivationEntity app = new AppActivationEntity();
////        app.setAppID("com.szqd.shoudiantong");
//        PlatformUser p = new PlatformUser();
//        p.setPlatformID(1);
//        List<AppActivationEntity> list = service.queryAppActivationInfoByPlatformIDList(p,app);
//        System.out.println(list);



//        AppActivationEntity e = new AppActivationEntity();
//        e.setAppName("闪记");
//        e.setAppID("com.szqd.shanji");
//        e.setDeviceID("666918055122994");
//        e.setPlatformID(1);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("闪记");
//        e.setAppID("com.szqd.shanji");
//        e.setDeviceID("666918055122994");
//        e.setPlatformID(2);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("闪记");
//        e.setAppID("com.szqd.shanji");
//        e.setDeviceID("777918055122994");
//        e.setPlatformID(1);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("闪记");
//        e.setAppID("com.szqd.shanji");
//        e.setDeviceID("777918055122994");
//        e.setPlatformID(2);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        ////
//
//        e = new AppActivationEntity();
//        e.setAppName("手电筒");
//        e.setAppID("com.szqd.shoudiantong");
//        e.setDeviceID("666918055122994");
//        e.setPlatformID(1);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("手电筒");
//        e.setAppID("com.szqd.shoudiantong");
//        e.setDeviceID("666918055122994");
//        e.setPlatformID(2);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("手电筒");
//        e.setAppID("com.szqd.shoudiantong");
//        e.setDeviceID("777918055122994");
//        e.setPlatformID(1);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);
//
//        e = new AppActivationEntity();
//        e.setAppName("手电筒");
//        e.setAppID("com.szqd.shoudiantong");
//        e.setDeviceID("777918055122994");
//        e.setPlatformID(2);
//        e.setCreateTime(Calendar.getInstance().getTimeInMillis());
//        service.addAppActivationInfo(e);






//        PlatformUser u = new PlatformUser();
//        u.setPlatformID(1);
//        u.setPlatformName("腾讯游戏中心");
//        u.setLoginName("tencent");
//        u.setLoginPwd("1111");
//        u.setIncremental(0);
//        u.setScale(1);
//        u.setRole(UsersRole.ROLE_MANAGER.getRoleId());
//        service.addPlatform(u);
//
//        u = new PlatformUser();
//        u.setPlatformID(2);
//        u.setPlatformName("360游戏平台");
//        u.setLoginName("360cn");
//        u.setLoginPwd("1111");
//        u.setIncremental(0);
//        u.setScale(1);
//        u.setRole(UsersRole.ROLE_MANAGER.getRoleId());
//        service.addPlatform(u);
//
//        u = new PlatformUser();
//        u.setPlatformID(null);
//        u.setPlatformName("管理员");
//        u.setLoginName("admin");
//        u.setLoginPwd("1111");
//        u.setIncremental(0);
//        u.setScale(1);
//        u.setRole(UsersRole.ROLE_ADMIN.getRoleId());
//        service.addPlatform(u);

//        PlatformUser u = new PlatformUser();
//        u.setPlatformID(1);
//        u.setPlatformName("壁纸管理");
//        u.setLoginName("user");
//        u.setLoginPwd("1111");
//        u.setIncremental(0f);
//        u.setScale(1f);
//        u.setRole(UsersRole.ROLE_USER.getRoleId());
//        service.addPlatform(u);

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sf.format(date));
    }
}
