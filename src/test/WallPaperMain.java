package test;

import com.szqd.project.mobile.lock.service.WallpaperService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by like on 4/15/15.
 */
public class WallPaperMain {
    public static void main(String[]args) throws Exception {
//        String path = Thread.currentThread().getContextClassLoader().getResource("com/szqd/framework/config/log4j.properties").getPath();
//        Log4jConfigurer.initLogging(path);
//        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");
//        WallpaperService wallpaperService = (WallpaperService)context.getBean("wallpaperService");
//        int testSeq = wallpaperService.getNextSequenceForFieldKey("test");
//        System.out.println("seq:"+testSeq);

        Thumbnails.of("/Users/like/Desktop/download-sourcepaper-pic.do.jpg").size(297,495).outputQuality(0.9f).toFile("/Users/like/Desktop/aa");


    }
}
