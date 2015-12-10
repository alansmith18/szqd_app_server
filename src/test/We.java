package test;

import com.szqd.framework.util.DateUtils;

import java.util.Date;

/**
 * Created by like on 7/23/15.
 */
public class We {
    public static void main(String[] args)throws Exception{
//        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");
//        TrackingService trackingService = (TrackingService)context.getBean("trackingService");
//        TrackingDataPOJO query = new TrackingDataPOJO();
//        query.setBeginDay("2015-11-20");
//        query.setEndDay("2015-11-22");
//        List<TrackingDataPOJO> list = trackingService.fetchTrackingForReport(query);
//        for (TrackingDataPOJO t : list) {
//            System.out.println(t.getCreateTimeText()+" : "+t.getNumberOfClick());
//        }
        Date begin = DateUtils.stringToDate("2015-01-30","yyyy-MM-dd");
        Date end = DateUtils.stringToDate("2015-01-29","yyyy-MM-dd");
        long diff = DateUtils.daysBetweenTwoDate(begin,end);
        System.out.println(diff);
    }
}
