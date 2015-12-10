package test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.szqd.framework.util.DateUtils;
import com.szqd.framework.util.URLConnectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by like on 4/7/15.
 */
public class WeatherMain {

    public static void main(String[] args) throws Exception {
//        String path = Thread.currentThread().getContextClassLoader().getResource("com/szqd/framework/config/log4j.properties").getPath();
//        Log4jConfigurer.initLogging(path);
//        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");

        //七天天气
//        String html = URLConnectionUtils.send("http://www.weather.com.cn/weather/101040100.shtml", null, "GET", "UTF-8");


        //获取天气预警页面
//        String html = URLConnectionUtils.send("http://product.weather.com.cn/alarm/grepalarm.php?areaid=10106[\\d]{0,2}", null, "GET", "UTF-8");

        //根据天气预警页面获取预警内容
//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=1012813-20150925130718-0702.html");
//        String html = page.asXml();

//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(50000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://www.weather.com.cn/air/?city=101040100");
//        String html = page.asXml();
//
//        Document document = Jsoup.parse(html);
//        Element element = document.select("div[class=airBot clearfix] > div.l").first();
//        String aqi = element.select("div.aqi > em").text();
//        String aqiText = element.select("div.aqi > i").text();
//
//        String pm10 = element.select("ul > li[class=li2 clearfix] > i.pm10").text();
//        String pm25 = element.select("ul > li[class=li2 clearfix] > i.pm25").text();
//        String co = element.select("ul > li[class=li2 clearfix] > i.co").text();
//        String no2 = element.select("ul > li[class=li2 clearfix] > i.no2").text();
//        String so2 = element.select("ul > li[class=li2 clearfix] > i.so2").text();
//
//        String aqiDesc = element.select("div.txt > span").text();
//
//        System.out.println("aqi:"+aqi);
//        System.out.println("aqiText:"+aqiText);
//        System.out.println("pm10:"+pm10);
//        System.out.println("pm25:"+pm25);
//        System.out.println("co:"+co);
//        System.out.println("no2:"+no2);
//        System.out.println("so2:"+so2);
//        System.out.println("aqiDesc:"+aqiDesc);


//        String response = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-of-seven-day.do", param, "POST", "UTF-8");
//        System.out.println(response);

//        String response = URLConnectionUtils.send("http://www.weather.com.cn/weather/101040100.shtml", param, "GET", "UTF-8");
//        param.put("html",response);
//        String result = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-of-seven-day.do", param, "POST", "UTF-8");
//        System.out.println(result);

//        String response = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-index-of-living.do", param, "POST", "UTF-8");
//        System.out.println(response);

//        String html = URLConnectionUtils.send("http://www.weather.com.cn/weather/101040100.shtml", param, "GET", "UTF-8");
//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(50000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://www.weather.com.cn/weather/101040100.shtml");
//        String html = page.asXml();
//        param.put("html",html);
//        String result = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-index-of-living.do", param, "POST", "UTF-8");
//        System.out.println(result);


//        String result = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-for-aqi.do", param, "POST", "UTF-8");
//        System.out.println(result);

//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(50000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://www.weather.com.cn/air/?city=101040100");
//        String html = page.asXml();
//        String html = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-for-aqi.do", param, "POST", "UTF-8");
//        param.put("html",html);
//        String result = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-for-aqi.do", param, "POST", "UTF-8");
//        System.out.println(result);

//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(50000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://product.weather.com.cn/alarm/grepalarm.php?areaid=10107[\\\\d]{0,2}");
//        String html = page.asXml();
//

//        param.put("html",html);
//
//        String html = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-for-disaster-warning.do", param, "POST", "UTF-8");
//        System.out.println(html);




//        String html = URLConnectionUtils.send("http://product.weather.com.cn/alarm/grepalarm.php?areaid=10107[\\d]{0,2}",null,"GET","UTF-8");
//        System.out.println(html);
        HashMap<String,String> param = new HashMap<>();
        param.put("cityID", "101070201");
        param.put("securityKey","SZQD_SECURITY_KEY_DQZS");
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(50000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page = wc.getPage("http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=1010713-20151110062200-1302.html");
        String html = page.asXml();
        param.put("html", html);
        html = URLConnectionUtils.send("http://localhost:9888/app/weather/fetch-weather-for-disaster-warning.do", param, "POST", "UTF-8");
        System.out.println(html);


//        String html = URLConnectionUtils.send("http://product.weather.com.cn/alarm/grepalarm.php?areaid=10107[\\d]{0,2}", null, "GET", "UTF-8");
//        System.out.println(html);
//        String html = URLConnectionUtils.send("http://www.weather.com.cn/weather/101070101.shtml",null,"GET","UTF-8");
//        System.out.println(html);
//        html = "var alarminfo={\"count\":\"7\",\"data\":[[\"辽宁省盘锦市\",\"1010713-20151110062200-1302.html\"],[\"辽宁省朝阳市\",\"1010712-20151110062000-1202.html\"],[\"辽宁省\",\"10107-20151110061200-1302.html\"],[\"辽宁省大连市\",\"1010702-20151110052000-0501.html\"],[\"辽宁省\",\"10107-20151109224200-1202.html\"],[\"辽宁省盘锦市\",\"1010713-20151109170634-0501.html\"],[\"辽宁省\",\"10107-20151109165500-0501.html\"]]}";

//        param.put("html", html);

//        String url = "http://192.168.1.156:9888/app";
//        String periodURL = url+"/weather/fetch-weather-in-period.do";
//        String sevenDayURL = url+"/weather/fetch-weather-of-seven-day.do ";
//        String alarmURL = url+"/weather/fetch-weather-for-disaster-warning.do";
//        String result = URLConnectionUtils.send(alarmURL, param, "POST", "UTF-8");
//        System.out.println(result);
//        String html = "var alarminfo={\"count\":\"7\",\"data\":[[\"辽宁省大连市\",\"1010702-20151109105000-0501.html\"],[\"辽宁省盘锦市\",\"1010713-20151109062834-0501.html\"],[\"辽宁省\",\"10107-20151109061400-1302.html\"],[\"辽宁省\",\"10107-20151109060700-0501.html\"],[\"辽宁省盘锦市\",\"1010713-20151108193900-1302.html\"],[\"辽宁省盘锦市\",\"1010713-20151108192300-1402.html\"],[\"辽宁省\",\"10107-20151108185800-1402.html\"]]}";
//        String html = URLConnectionUtils.send("http://product.weather.com.cn/alarm/grepalarm.php?areaid=10107[\\d]{0,2}",null,"GET","UTF-8");
//        System.out.println(html);

    }


    public static Date calculateExpireDateForSevenDayWeather()
    {
        Calendar now = Calendar.getInstance();
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay >= 0 && hourOfDay <= 7)
        {
            return DateUtils.getTodayForSpecifiedTime(8, 0, 0, 0).getTime();
        }
        else if(hourOfDay >= 8 && hourOfDay <=11)
        {
            return DateUtils.getTodayForSpecifiedTime(12,0,0,0).getTime();
        }
        else if(hourOfDay >= 12 && hourOfDay <= 17)
        {
            return DateUtils.getTodayForSpecifiedTime(18,0,0,0).getTime();
        }
        else if (hourOfDay >= 18 && hourOfDay <=22)
        {
            return DateUtils.getTodayForSpecifiedTime(23,0,0,0).getTime();
        }
        else {
            return DateUtils.getTomorrowForSpecifiedTime(8,0,0,0).getTime();
        }

    }

}
