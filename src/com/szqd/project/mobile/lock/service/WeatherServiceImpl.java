package com.szqd.project.mobile.lock.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.BeanUtil;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.mobile.lock.model.json.weather.AllWeather;
import com.szqd.project.mobile.lock.model.json.weather.WeatherEntity;
import com.szqd.project.mobile.lock.model.weather.*;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.util.UrlUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class WeatherServiceImpl extends SuperService implements WeatherService {

    private static Logger logger = Logger.getLogger(WeatherServiceImpl.class);


    private static final String WEATHER_KEY_PREFIX = "mlweather:";
    /**
     * 通过redis获取缓存天气数据
     * @param cityID
     * @return
     */
    public AllWeather getAllWeatherFromRedis(String cityID)
    {
        try {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            String cityKey = WEATHER_KEY_PREFIX + cityID;
//            redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
            BoundHashOperations hashOps = redisTemplate.boundHashOps(cityKey);

            String cityChineseName = (String)hashOps.get("cityChineseName");
            String disasterDetail = (String)hashOps.get("disasterDetail");
            String disasterLevel = (String)hashOps.get("disasterLevel");
            String disasterTitle = (String)hashOps.get("disasterTitle");

            if (cityChineseName == null)
            {
                return null;
            }
            
            AllWeather allWeather = new AllWeather();
            allWeather.setCityID(cityID);
            allWeather.setCityChineseName(cityChineseName);
            allWeather.setDisasterDetail(disasterDetail);
            allWeather.setDisasterLevel(disasterLevel);
            allWeather.setDisasterTitle(disasterTitle);

            List<WeatherEntity> weekWeather = new ArrayList<WeatherEntity>();
            allWeather.setWeekWeather(weekWeather);
            for (int i = 0 ; i < 6 ; i++)
            {
                String cityWeatherKey = cityKey + i;
                BoundHashOperations weatherHash = redisTemplate.boundHashOps(cityWeatherKey);
                List<String> values = weatherHash.multiGet(Arrays.asList("pmLevel", "temperature", "weatherText", "weekDayText", "realtimeTemp"));
                WeatherEntity weather = new WeatherEntity();
                weather.setPmLevel(values.get(0));
                weather.setTemperature(values.get(1));
                weather.setWeatherText(values.get(2));
                weather.setWeekDayText(values.get(3));
                weather.setRealtimeTemp(values.get(4));
                weekWeather.add(weather);
            }
            return allWeather;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.getAllWeatherFromRedis(String cityID)",e);
            throw new RuntimeException("通过redis获取缓存天气数据出错");
        }
    }

    /**
     * 将客户端json数据保存到缓存
     * @param jsonString
     * @param sourceType
     */
    public void addWeatherToDb(String jsonString ,Integer sourceType)
    {
        try
        {

            AllWeather allWeather = null;
            if (sourceType == 1)
            {
                allWeather = this.convertXiaoMiWeatherJson(jsonString);
            }
            else if (sourceType == 2)
            {
                allWeather = this.convertDuBaWeatherJson(jsonString);
            }
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            String cityKey = WEATHER_KEY_PREFIX + allWeather.getCityID();
            BoundHashOperations hashOps = redisTemplate.boundHashOps(cityKey);

//            Calendar tomorrow = Calendar.getInstance();
//            tomorrow.add(Calendar.DAY_OF_MONTH, 1);
//            tomorrow.set(Calendar.HOUR_OF_DAY, 0);
//            tomorrow.set(Calendar.MINUTE, 0);
//            tomorrow.set(Calendar.SECOND, 0);

            if (logger.isDebugEnabled())
            {
//                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String datestring= sf.format(tomorrow.getTime());
//                logger.debug(datestring);
            }



            redisTemplate.expire(cityKey, 2, TimeUnit.HOURS);
//            redisTemplate.expireAt(cityKey, tomorrow.getTime());
            Map map = new HashMap();
            map.put("cityChineseName",allWeather.getCityChineseName());

            if (allWeather.getDisasterLevel()!= null) {
                map.put("disasterDetail", allWeather.getDisasterDetail());
                map.put("disasterLevel", allWeather.getDisasterLevel());
                map.put("disasterTitle", allWeather.getDisasterTitle());
            }
            hashOps.putAll(map);

            List<WeatherEntity> weekWeatherList = allWeather.getWeekWeather();
            for (int i = 0; i < weekWeatherList.size(); i++)
            {
                WeatherEntity weather = weekWeatherList.get(i);
                String cityWeatherKey = cityKey + i;
                BoundHashOperations weatherHash = redisTemplate.boundHashOps(cityWeatherKey);
//                redisTemplate.expireAt(cityWeatherKey, tomorrow.getTime());
                redisTemplate.expire(cityWeatherKey, 2, TimeUnit.HOURS);
                Map weatherMap = new HashMap();
                weatherMap.put("pmLevel", weather.getPmLevel());
                weatherMap.put("temperature", weather.getTemperature());
                weatherMap.put("weatherText", weather.getWeatherText());
                weatherMap.put("weekDayText", weather.getWeekDayText());
                if (sourceType == 1) {
                    weatherMap.put("realtimeTemp", weather.getRealtimeTemp());
                }

//                redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
                weatherHash.putAll(weatherMap);

            }
            logger.debug("保存了天气");
        } catch (Exception e) {
            logger.error(jsonString);
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.addWeatherToDb(String jsonString ,Integer sourceType)",e);
            throw new RuntimeException("将客户端json数据保存到缓存出错");
        }

    }

    private AllWeather convertDuBaWeatherJson(String jsonString)
    {
        try
        {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(jsonObject);
            MorphDynaBean weatherinfo = (MorphDynaBean)bean.get("weatherinfo");
            String cityChineseName = (String)weatherinfo.get("city");
            String cityid = (String)weatherinfo.get("cityid");
            String dateString = (String)weatherinfo.get("date_y");
            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = sf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            AllWeather allWeather = new AllWeather();
            allWeather.setCityID(cityid);
            allWeather.setCityChineseName(cityChineseName);
            List<WeatherEntity> list = new ArrayList<WeatherEntity>();
            allWeather.setWeekWeather(list);


            for (int i = 0 ; i <= 5; i++)
            {
                WeatherEntity weather = new WeatherEntity();

                int week = calendar.get(Calendar.DAY_OF_WEEK);
                String weekText = this.getWeekText(week);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                weather.setWeekDayText(weekText);

                String weatherText = "";
                String temperature = "";
                String pmLevel = "";
//                String realTemp = null;
                switch (i)
                {
                    case 0:
                    {
                        weatherText = (String)weatherinfo.get("weather1");
                        temperature = (String)weatherinfo.get("temp1");
                        pmLevel = (String)weatherinfo.get("pm-level");
//                        realTemp = (String)weatherinfo.get("st1");
                        break;
                    }
                    case 1:
                    {
                        weatherText = (String)weatherinfo.get("weather2");
                        temperature = (String)weatherinfo.get("temp2");
                        break;
                    }
                    case 2:
                    {
                        weatherText = (String)weatherinfo.get("weather3");
                        temperature = (String)weatherinfo.get("temp3");
                        break;
                    }
                    case 3:
                    {
                        weatherText = (String)weatherinfo.get("weather4");
                        temperature = (String)weatherinfo.get("temp4");
                        break;
                    }
                    case 4:
                    {
                        weatherText = (String)weatherinfo.get("weather5");
                        temperature = (String)weatherinfo.get("temp5");
                        break;
                    }
                    case 5:
                    {
                        weatherText = (String)weatherinfo.get("weather6");
                        temperature = (String)weatherinfo.get("temp6");
                        break;
                    }
                }
                weather.setWeatherText(weatherText);
                weather.setTemperature(temperature);
                weather.setPmLevel(pmLevel);
//                weather.setRealtimeTemp(realTemp);
                list.add(weather);
            }
            return allWeather;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.convertDuBaWeatherJson(String jsonString)",e);
            throw new RuntimeException("解析毒霸天气出错");
        }


    }

    private AllWeather convertXiaoMiWeatherJson(String jsonString)
    {

        try
        {
            JSONObject jsonObject = JSONObject.fromObject(jsonString);
            MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(jsonObject);
            MorphDynaBean forecast = (MorphDynaBean)bean.get("forecast");
            MorphDynaBean aqi = (MorphDynaBean)bean.get("aqi");
            MorphDynaBean realtime = (MorphDynaBean)bean.get("realtime");
            List<MorphDynaBean> alert = (List<MorphDynaBean>)bean.get("alert");
            String detail = null;
            String level = null;
            String title = null;
            if (alert.size() != 0) {
                MorphDynaBean alert0 = alert.get(0);
                detail = (String)alert0.get("detail");
                level = (String)alert0.get("level");
                title = (String)alert0.get("title");
            }


            String cityChineseName = (String)forecast.get("city");
            String cityid = (String)forecast.get("cityid");
            String dateString = (String)forecast.get("date_y");
            Calendar calendar = null;
            try {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = sf.parse(dateString);
                calendar = Calendar.getInstance();
                calendar.setTime(date);
            } catch (Exception e) {
                calendar = Calendar.getInstance();
            }

            AllWeather allWeather = new AllWeather();
            allWeather.setCityID(cityid);
            allWeather.setCityChineseName(cityChineseName);
            List<WeatherEntity> list = new ArrayList<WeatherEntity>();
            allWeather.setWeekWeather(list);
            allWeather.setDisasterDetail(detail);
            allWeather.setDisasterTitle(title);
            allWeather.setDisasterLevel(level);


            for (int i = 0 ; i <= 5; i++)
            {
                WeatherEntity weather = new WeatherEntity();

                int week = calendar.get(Calendar.DAY_OF_WEEK);
                String weekText = this.getWeekText(week);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                weather.setWeekDayText(weekText);

                String weatherText = "";
                String temperature = "";

                String realTemp = "";
                String pmLevel = "";


                switch (i)
                {
                    case 0:
                    {
                        weatherText = (String)forecast.get("weather1");
                        temperature = (String)forecast.get("temp1");
                        Integer aqiValue = null;
                        try {
                            aqiValue = Integer.valueOf((String) aqi.get("aqi"));
                            pmLevel = this.apiToString(aqiValue);
                        } catch (Exception e) {
                            pmLevel = "无";
                        }

                        realTemp = (String)realtime.get("temp");
                        break;
                    }
                    case 1:
                    {
                        weatherText = (String)forecast.get("weather2");
                        temperature = (String)forecast.get("temp2");
                        break;
                    }
                    case 2:
                    {
                        weatherText = (String)forecast.get("weather3");
                        temperature = (String)forecast.get("temp3");
                        break;
                    }
                    case 3:
                    {
                        weatherText = (String)forecast.get("weather4");
                        temperature = (String)forecast.get("temp4");
                        break;
                    }
                    case 4:
                    {
                        weatherText = (String)forecast.get("weather5");
                        temperature = (String)forecast.get("temp5");

                        break;
                    }
                    case 5:
                    {
                        weatherText = (String)forecast.get("weather6");
//                        temperature = (String)forecast.get("temp6");

                        break;
                    }
                }
                weather.setWeatherText(weatherText);
                weather.setTemperature(temperature);
                weather.setPmLevel(pmLevel);
                weather.setRealtimeTemp(realTemp);
                list.add(weather);
            }

            return allWeather;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.convertXiaoMiWeatherJson(String jsonString)",e);
            throw new RuntimeException("解析小米天气的json出错");
        }

    }

    private String apiToString(int aqi){
        if(aqi>300){
            return "严重污染";
        }else if(aqi>200){
            return "重度污染";
        }else if(aqi>150){
            return "中度污染";
        }else if(aqi>100){
            return "轻度污染";
        }else if(aqi>50){
            return "良";
        }else{
            return "优";
        }
    }

    private static final String MONDAY_TEXT = "星期一";
    private static final String TUESDAY_TEXT = "星期二";
    private static final String WEDNESDAY_TEXT = "星期三";
    private static final String THURSDAY_TEXT = "星期四";
    private static final String FRIDAY_TEXT = "星期五";
    private static final String SATURDAY_TEXT = "星期六";
    private static final String SUNDAY_TEXT = "星期日";
    private String getWeekText(int week)
    {
        switch (week)
        {
            case Calendar.MONDAY:
            {
                return MONDAY_TEXT;
            }
            case Calendar.TUESDAY:
            {
                return TUESDAY_TEXT;
            }
            case Calendar.WEDNESDAY:
            {
                return WEDNESDAY_TEXT;
            }
            case Calendar.THURSDAY:
            {
                return THURSDAY_TEXT;
            }
            case Calendar.FRIDAY:
            {
                return FRIDAY_TEXT;
            }
            case Calendar.SATURDAY:
            {
                return SATURDAY_TEXT;
            }
            case Calendar.SUNDAY:
            {
                return SUNDAY_TEXT;
            }

        }

        return null;
    }



    /**
     * 读取json
     * @return
     */
    public String readJsonFromServer(String city,String language,String unit,String aqi,String alarm)
    {

        StringBuilder jsonString = new StringBuilder();

        HttpURLConnection urlConnection = null;

        try
        {
            String urlString = null;//WeatherURL.getURL(city,language,unit,aqi,alarm);
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(12000);
            urlConnection.setReadTimeout(12000);
            urlConnection.setDefaultUseCaches(false);
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(false);
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            urlConnection.setRequestMethod("GET");
            System.setProperty("http.keepAlive", "false");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            byte[] data = null;
            boolean isReadFromServerNotEnd = true;
            do
            {
                data = new byte[1024*2];
                int readNumberOfByte = inputStream.read(data);
                isReadFromServerNotEnd = readNumberOfByte != -1;
                if (isReadFromServerNotEnd)
                {
                    jsonString.append(new String(data,0,readNumberOfByte,"UTF-8"));
                }
            }
            while (isReadFromServerNotEnd);
            inputStream.close();

        } catch (Exception e) {
            logger.error("异常位置为:com.szqd.project.mobile.lock.service.WeatherServiceImpl.readJsonFromServer()",e);
            throw new RuntimeException("从smartWeather获取天气数据出错");
        }
        finally {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }

        }

        return jsonString.toString();
    }

    private static final String SEVEN_DAY_WEATHER_URL_PREFIX = "http://www.weather.com.cn/weather/%s.shtml";
    /**
     * 获取七天天气的url
     * @param cityID
     * @return
     */
    public String getSevenDayWeatherURL(String cityID)
    {
        String sevenDayWeatherURL = String.format(SEVEN_DAY_WEATHER_URL_PREFIX, cityID);
        return sevenDayWeatherURL;
    }

    /**
     * http://www.weather.com.cn/weather/101040100.shtml
     * 七天基本天气
     * @param html
     */
    public List<SevenDayWeather> parseSevenDayWeather(String html)
    {
        List<SevenDayWeather> sevenDayWeathers = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#7d > ul > li");
        for (int i = 0; i < elements.size(); i++) {
            Element element =  elements.get(i);
            String weekDayText = element.select("h1").get(0).html().trim();
            String dateText = element.select("h2").get(0).html().trim();
            String wea = element.select("p.wea").get(0).html().trim();

            String maxTemp = element.select(("p[class=tem tem1] > span")).get(0).html().trim();
            String lowestTemp = element.select(("p[class=tem tem2] > span")).get(0).html().trim();
            String wind = element.select("p[class=win] > i").get(0).html().trim();

            SevenDayWeather sevenDayWeather = new SevenDayWeather();
            sevenDayWeather.setWeekDayText(weekDayText);
            sevenDayWeather.setDateText(dateText);
            sevenDayWeather.setWeather(wea);
            sevenDayWeather.setMaximumTemperature(maxTemp);
            sevenDayWeather.setLowestTemperature(lowestTemp);
            sevenDayWeather.setWindPower(wind);
            sevenDayWeathers.add(sevenDayWeather);

            if ("".equals(weekDayText)) throw new RuntimeException("数据为空");
        }

        return sevenDayWeathers;

    }


    private static final String INDEX_OF_LIVING_URL_PREFIX = "http://www.weather.com.cn/weather/%s.shtml";

    /**
     * 获取生活指数的url
     * @param cityID
     * @return
     */
    public String getIndexOfLivingUrl(String cityID)
    {
        String indexOfLivingURL = String.format(INDEX_OF_LIVING_URL_PREFIX,cityID);
        return indexOfLivingURL;
    }
    /**
     * http://www.weather.com.cn/weather/101040100.shtml
     * 生活指数
     * @param html
     */
    public List<IndexOfLiving> parseIndexOfLiving(String html) {

        List<IndexOfLiving> livingList = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Elements elements = document.select("div#zs > ul > li");
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String titleForIndexOfLiving = element.select("h3 > a").first().html().trim();
            logger.debug(titleForIndexOfLiving);

            Element aside = null;
            String title = null;
            switch (i){
                case 0://眼镜指数
                    aside = element.select("section[class=mask gl] > section > section > aside[data-dn=7d1]").first();
                    title = "眼镜指数";
                    break;
                case 1://穿衣指数
                    aside = element.select("section[class=mask ct] > section > section > aside[data-dn=7d1]").first();
                    title = "穿衣指数";
                    break;
                case 2://旅游指数
                    aside = element.select("section[class=mask tr] > section > section > aside[data-dn=7d1]").first();
                    title = "旅游指数";
                    break;
                case 3://运动指数
                    aside = element.select("section[class=mask yd] > section > section > aside[data-dn=7d1]").first();
                    title = "运动指数";
                    break;
                case 4://洗车指数
                    aside = element.select("section[class=mask xc] > section > section > aside[data-dn=7d1]").first();
                    title = "洗车指数";
                    break;
                case 5://化妆指数
                    aside = element.select("section[class=mask pp] > section > section > aside[data-dn=7d1]").first();
                    title = "化妆指数";
                    break;
                case 6://感冒指数
                    aside = element.select("section[class=mask gm] > section > section > aside[data-dn=7d1]").first();
                    title = "感冒指数";
                    break;
                case 7://紫外线指数
                    aside = element.select("section[class=mask uv] > section > section > aside[data-dn=7d1]").first();
                    title = "紫外线指数";
                    break;
                case 8://舒适指数
                    aside = element.select("section[class=mask co] > section > section > aside[data-dn=7d1]").first();
                    title = "舒适指数";
                    break;

            }
            IndexOfLiving indexOfLiving = new IndexOfLiving();
            indexOfLiving.setTitle(title);
            String info = aside.child(0).text().trim();;
            String action = aside.text().replaceFirst(info, "").trim();
            indexOfLiving.setInfo(info);
            indexOfLiving.setAction(action);
            livingList.add(indexOfLiving);
            logger.debug(info + ":" + action);

            if ("".equals(info)) throw new RuntimeException("数据为空");
        }
        return livingList;
    }

    private static final String WEATHER_FOR_PERIOD_URL_PREFIX = "http://www.weather.com.cn/weather/%s.shtml";

    /**
     * 获取区间时间天气的url
     * @param cityID
     * @return
     */
    public String getWeatherForPeriodUrl(String cityID)
    {
        String weatherForPeriodURL = String.format(WEATHER_FOR_PERIOD_URL_PREFIX,cityID);
        return weatherForPeriodURL;
    }

    /**
     * 今天的区间天气
     * http://www.weather.com.cn/weather/101040100.shtml
     * @param html
     */
    public List<WeatherForPeriod> parseWeatherForRecentPeriod(String html)
    {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("ul#Hour3 > li[data-dn^=today] ");

        List<WeatherForPeriod> weatherForPeriodList = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String time = element.select("i").html().replace("|", "").replace("&nbsp;", "").trim();
            String weather = element.select("em").text().trim();
            String temp = element.select("span").get(0).text().trim();
            String windDirection = element.select("b").text().trim();
            String windPower = element.select("strong").text().trim();
            logger.debug(time + ":" + weather + ":" + windDirection + ":" + windPower);

            if("".equals(time)) throw new RuntimeException("html数据为空");

            WeatherForPeriod weatherForPeriod = new WeatherForPeriod();
            weatherForPeriod.setTime(time);
            weatherForPeriod.setWeather(weather);
            weatherForPeriod.setTemperature(temp);
            weatherForPeriod.setWindDirection(windDirection);
            weatherForPeriod.setWindPower(windPower);
            weatherForPeriodList.add(weatherForPeriod);
        }

        return weatherForPeriodList;
    }

    private static final String ALARM_FOR_PAGE_URL_PREFIX = "http://product.weather.com.cn/alarm/grepalarm.php?areaid=%s[\\d]{0,2}";

    public String getAlarmURL(String cityID)
    {
        String city = cityID.substring(0,5);
        String alarmForPageURL = String.format(ALARM_FOR_PAGE_URL_PREFIX, city);
        return alarmForPageURL;
    }


    /**
     * http://product.weather.com.cn/alarm/grepalarm.php?areaid=10106[\\d]{0,2}
     * 获取灾害信息的列表页面
     * @param html
     * @throws Exception
     */
    public List<String> parseAlarmForPage(String html)
    {
        List<String> alarmList = null;
        try {
            alarmList = new ArrayList<>();
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine se = sem.getEngineByName("javascript");
            se.eval(html);
            ScriptObjectMirror object = (ScriptObjectMirror)se.get("alarminfo");
            String alarmCount = (String)object.get("count");
            ScriptObjectMirror data = (ScriptObjectMirror) object.get("data");
            for (int i = 0; i < Integer.valueOf(alarmCount); i++)
            {
                ScriptObjectMirror dataVal = (ScriptObjectMirror)data.getSlot(i);
                String area = (String)dataVal.get(0);
                String pageURI = (String)dataVal.get(1);

    //            pageMap.put("area",area);
                StringBuilder page = new StringBuilder();
                page.append(ALARM_INFO_URL_PREFIX).append((String)dataVal.get(1));

                alarmList.add(page.toString());

                logger.debug(area + ":" + pageURI);

            }
        } catch (ScriptException e) {

        }

        return alarmList;
    }

    private static final String ALARM_INFO_URL_PREFIX = "http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=";

    /**
     * http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=1012813-20150925130718-0702.html
     * 通过灾害信息的页面获取灾害信息
     * @param pageURI
     * @return
     * @throws Exception
     */
    public String catchAlarmInfo(String pageURI)throws Exception
    {
        WebClient wc = new WebClient();
        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); //禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page = wc.getPage(ALARM_INFO_URL_PREFIX+pageURI);
        String html = page.asXml();
        return html;
    }

    /**
     * http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=1012813-20150925130718-0702.html
     * 通过灾害信息的页面获取灾害信息
     * @param html
     */
    public List<AlarmInfo> parseAlarmInfo(String html)
    {
        List<AlarmInfo> alarmList = new ArrayList<>();
        Document document = Jsoup.parse(html);
        Elements titleElements = document.select("#alarmtitle");
        AlarmInfo alarmEntity = new AlarmInfo();
        for (int i = 0; i < titleElements.size(); i++) {
            Element element = titleElements.get(i);
            String alarmTitle = element.text();
            alarmEntity.setAlarmTitle(alarmTitle);
            logger.debug(alarmTitle);
        }
        Elements contentElements = document.select("#alarmcontent");
        for (int i = 0; i < contentElements.size(); i++) {
            Element element = contentElements.get(i);
            String alarmInfo = element.text();
            alarmEntity.setAlarmInfo(alarmInfo);
            logger.debug(alarmInfo);
        }
        alarmList.add(alarmEntity);
        return alarmList;
    }


    public List parseAlarm(String html)
    {
        List parseAlarmInfoList = this.parseAlarmInfo(html);
        if (parseAlarmInfoList.isEmpty())
        {
            parseAlarmInfoList = this.parseAlarmForPage(html);
        }
        return parseAlarmInfoList;
    }

    private static final String AQI_URL_PREFIX = "http://www.weather.com.cn/air/?city=%s";
    public String getAQIUrl(String cityID)
    {
        String aqiURL = String.format(AQI_URL_PREFIX, cityID);
        return aqiURL;
    }

    /**
     * http://www.weather.com.cn/air/?city=101020100
     * 获取到api信息
     * @param html
     */
    public List<WeatherForAQI> parseAqi(String html)
    {
        Document document = Jsoup.parse(html);
        Element element = document.select("div[class=airBot clearfix] > div.l").first();
        if (element == null) throw new RuntimeException("暂无空气质量数据");
        String aqi = element.select("div.aqi > em").text().trim();
        if ("".equals(aqi)) throw new RuntimeException("AQI数据为空");
        String aqiText = element.select("div.aqi > i").text().trim();

        String pm10 = element.select("ul > li[class=li2 clearfix] > i.pm10").text().trim();
        String pm25 = element.select("ul > li[class=li2 clearfix] > i.pm25").text().trim();
        String co = element.select("ul > li[class=li2 clearfix] > i.co").text().trim();
        String no2 = element.select("ul > li[class=li2 clearfix] > i.no2").text().trim();
        String so2 = element.select("ul > li[class=li2 clearfix] > i.so2").text().trim();

        String aqiDesc = element.select("div.txt > span").text().trim();

        logger.debug("aqi:" + aqi);
        logger.debug("aqiText:" + aqiText);
        logger.debug("pm10:" + pm10);
        logger.debug("pm25:" + pm25);
        logger.debug("co:" + co);
        logger.debug("no2:" + no2);
        logger.debug("so2:" + so2);
        logger.debug("aqiDesc:" + aqiDesc);

        WeatherForAQI weatherForAQI = new WeatherForAQI();
        weatherForAQI.setAqi(aqi);
        weatherForAQI.setAqiText(aqiText);
        weatherForAQI.setPm10(pm10);
        weatherForAQI.setPm25(pm25);
        weatherForAQI.setCo(co);
        weatherForAQI.setNo2(no2);
        weatherForAQI.setSo2(so2);
        weatherForAQI.setAqiDesc(aqiDesc);
        List<WeatherForAQI> list = new ArrayList<>();
        list.add(weatherForAQI);
        return list;
    }


    private static final String NEW_WEATHER_CONTENT_KEY_FOR_CITY_PREFIX = "NEW_WEATHER_CONTENT_KEY_FOR_CITY_%s";
    private String getWeatherKeyForCity(String cityID)
    {
        String weatherKeyForCity = String.format(NEW_WEATHER_CONTENT_KEY_FOR_CITY_PREFIX,cityID);
        return weatherKeyForCity;
    }


    public Map<String,Object> getWeather(String cityID,String weatherFieldForCity,Class entityClass)
    {
        Map<String,Object> map = null;
        try {
            map = new HashMap<>();
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(getWeatherKeyForCity(cityID));

            List weatherList = null;

            Object weatherObject = boundHashOps.get(weatherFieldForCity);
            boolean hasWeather = weatherObject != null;
            if (hasWeather)
            {
                List<Map> list = (List<Map>)weatherObject;
                weatherList = new BeanUtil().convertMapListToBeanList(list, entityClass);
                map.put("weatherContent",weatherList);
            }
            else
            {
                map.put("task", getURLByFieldAndCity(weatherFieldForCity,cityID));
            }
            return map;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.getWeather(String cityID,String weatherFieldForCity,Class entityClass)",e);
            throw new RuntimeException("ERROR:获取天气出错");
        }

    }

    private List<String> getURLByFieldAndCity(String field,String cityID)
    {
        if (NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER.equals(field))
        {
            return Arrays.asList(this.getSevenDayWeatherURL(cityID));
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING.equals(field))
        {
            return Arrays.asList(this.getIndexOfLivingUrl(cityID));
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD.equals(field))
        {
            return Arrays.asList(this.getWeatherForPeriodUrl(cityID));
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_AQI.equals(field))
        {
            return Arrays.asList(this.getAQIUrl(cityID));
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING.equals(field))
        {
            return Arrays.asList(this.getAlarmURL(cityID));
        }

        return null;
    }

    private boolean checkIsUrlForParseList(List parse)
    {
        for (int i = 0; i < parse.size(); i++) {
            Object o = parse.get(i);
            if (o instanceof String)
            {
                String url = (String)o;
                boolean isURL = UrlUtils.isAbsoluteUrl(url);
                return isURL;
            }

        }
        return false;
    }

    public List setWeather(String cityID,String weatherFieldForCity, String html,Date expireDate)
    {
        try
        {
            List parseList = parseWeather(weatherFieldForCity,html);

            if (checkIsUrlForParseList(parseList))
            {
                return parseList;
            }

            if (parseList != null && parseList.size() != 0)
            {
                RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
                BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(getWeatherKeyForCity(cityID));
                boundHashOps.putIfAbsent(weatherFieldForCity,parseList);
                Long expire = boundHashOps.getExpire();
                boolean isExpireNotSet = expire == null || new Long(-1).equals(expire);
                if (isExpireNotSet) {
                    boundHashOps.expireAt(expireDate);
                }
            }
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.WeatherServiceImpl.setWeather(String cityID,String weatherFieldForCity, String html)",e);
            throw new RuntimeException("ERROR:设置天气出错");
        }

        return null;
    }

    private List parseWeather(String weatherFieldForCity,String html)
    {
        if (NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER.equals(weatherFieldForCity))
        {
            return this.parseSevenDayWeather(html);
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING.equals(weatherFieldForCity))
        {
            return this.parseIndexOfLiving(html);
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD.equals(weatherFieldForCity))
        {
            return this.parseWeatherForRecentPeriod(html);
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_AQI.equals(weatherFieldForCity))
        {
            return this.parseAqi(html);
        }
        else if (NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING.equals(weatherFieldForCity))
        {
            return this.parseAlarm(html);
        }

        return null;
    }

    /**
     * 七天
     */
    private static final String NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER = "NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER";

    /**
     * 生活指数
     */
    private static final String NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING = "NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING";

    /**
     * 小时区间天气
     */
    private static final String NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD = "NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD";

    /**
     * 空气质量AQI
     */
    private static final String NEW_WEATHER_CONTENT_FIELD_FOR_AQI = "NEW_WEATHER_CONTENT_FIELD_FOR_AQI";

    /**
     * 灾害预警
     */
    private static final String NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING = "NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING";

    /**
     * 获取七天天气,如果没有天气,则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getSevenDayWeather(String cityID)
    {
        return this.getWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER,SevenDayWeather.class);
    }

    /**
     * 设置七天天气
     * @param cityID
     * @param html
     */
    public void setWeatherOfSevenDay(String cityID, String html)
    {
        this.setWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_SEVEN_DAY_WEATHER,html,calculateExpireDateForSevenDayWeather());
    }

    /**
     * 获取生活指数,如果没有, 则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getIndexOfLiving(String cityID)
    {
        return this.getWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING,IndexOfLiving.class);
    }

    /**
     * 设置生活指数
     * @param cityID
     * @param html
     */
    public void setIndexOfLiving(String cityID, String html)
    {
        this.setWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_INDEX_OF_LIVING,html,calculateExpireDateForSevenDayWeather());
    }

    /**
     * 获取小时区间天气,如果没有则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getWeatherInPeriod(String cityID)
    {
        return this.getWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD,WeatherForPeriod.class);
    }

    /**
     * 设置小时区间天气
     * @param cityID
     * @param html
     */
    public void setWeatherInPeriod(String cityID,String html)
    {
        this.setWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_WEATHER_IN_PERIOD,html,calculateExpireDateForSevenDayWeather());
    }

    /**
     * 获取AQI,如果没有则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getWeatherForAQI(String cityID)
    {
        return this.getWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_AQI,WeatherForAQI.class);
    }

    /**
     * 设置AQI
     * @param cityID
     * @param html
     */
    public void setWeatherForAQI(String cityID,String html)
    {
        this.setWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_AQI,html,calculateExpireDateForSevenDayWeather());
    }

//    /**
//     * 设置灾害预警
//     * @param cityID
//     * @param html
//     */
//    public List setDisasterWarning(String cityID,String html)
//    {
//        List parseList = parseWeather(NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING,html);
//
//        if (checkIsUrlForParseList(parseList))
//        {
//            return parseList;
//        }
//        //this.setWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING,html,calculateExpireDateForSevenDayWeather());
//    }

    /**
     * 获取灾害预警
     * @param cityID
     * @param html
     * @return
     */
    public Map<String,Object> getDisasterWarning(String cityID,String html)
    {
        HashMap<String,Object> map = new HashMap<>();


        if (html == null)
        {
            map.put("task", Arrays.asList(this.getAlarmURL(cityID)));
            return map;
        }

        String dataKey = "weatherContent";
        List parseList = parseWeather(NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING,html);

        if (checkIsUrlForParseList(parseList))
        {
            dataKey = "task";
        }
        map.put(dataKey,parseList);

        return map;
//        return this.getWeather(cityID,NEW_WEATHER_CONTENT_FIELD_FOR_DISASTER_WARNING, AlarmInfo.class);
    }

    /**
     * 计算七天天气的过期时间
     * @return
     */
    public Date calculateExpireDateForSevenDayWeather()
    {
        Calendar now = Calendar.getInstance();
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);
        if (hourOfDay >= 0 && hourOfDay <= 7)
        {
            return DateUtils.getTodayForSpecifiedTime(8,0,0,0).getTime();
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
