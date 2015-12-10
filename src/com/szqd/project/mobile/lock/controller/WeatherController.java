package com.szqd.project.mobile.lock.controller;

import com.szqd.framework.config.SystemConfigParameter;
import com.szqd.framework.controller.SpringMVCController;
import com.szqd.project.mobile.lock.model.json.weather.AllWeather;
import com.szqd.project.mobile.lock.service.WeatherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by like on 15/3/18.
 */
@RestController
@RequestMapping("/weather")
public class WeatherController extends SpringMVCController
{

    private WeatherService weatherService = null;

    public WeatherService getWeatherService() {
        return weatherService;
    }

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/fetch-weather.do", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public AllWeather fetchWeather(String city)
    {

        AllWeather allWeather = this.weatherService.getAllWeatherFromRedis(city);
        return allWeather;
    }

    private static final String WEATHER_FROM_CLIENT_ERROR = "客户端传递的天气数据有误:jsonSourceType:%s  ##  weatherJson:%s";
    @RequestMapping(value = "/receive-clientjson.do",method = RequestMethod.POST)
    public void getWeatherFromClient(String clientJson, Integer jsonSourceType)
    {
        boolean isIncorrect = clientJson == null || clientJson.trim().equals("") || jsonSourceType == null;
        if (isIncorrect)
        {
//            return;
            throw new RuntimeException(String.format(WEATHER_FROM_CLIENT_ERROR,jsonSourceType,clientJson));
        }

        this.weatherService.addWeatherToDb(clientJson,jsonSourceType);

    }

    private static final String XIAOMI_URI = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=";
    private static final String DUBA_URI = "http://weather.123.duba.net/static/weather_info/";
    @RequestMapping(value = "/get-urls.do",method = RequestMethod.GET)
    public List<String> getUrls()
    {
        List<String> urls = Arrays.asList(XIAOMI_URI,DUBA_URI);
        return urls;
    }

    @RequestMapping(value = "/swt-resource.do")
    public List<String> getSecurityUrls()
    {
        char[]chars = XIAOMI_URI.toCharArray();
        StringBuilder securityHttpAddress = new StringBuilder();
        for (int v : chars)
        {
            char c = (char)(v << 2);
            securityHttpAddress.append(c);
        }
        String securityXiaomi = securityHttpAddress.toString();

        chars = DUBA_URI.toCharArray();
        securityHttpAddress = new StringBuilder();
        for (int v : chars)
        {
            char c = (char)(v << 2);
            securityHttpAddress.append(c);
        }
        String securityDuba = securityHttpAddress.toString();

        List<String> swtResource = Arrays.asList(securityXiaomi,securityDuba);

        return swtResource;
    }

    private static final String ENABLE_WEATHER_PAGE = "/module/weather/weather-enable.jsp";
    public static boolean isEnableWeather = true;
    public static boolean isEnableFlashlightAdvertising = true;

    @RequestMapping("/is-enable-wt.do")
    public ModelAndView isEnableWeather()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isEnableWeather",isEnableWeather);
        modelAndView.addObject("isEnableFlashlightAdvertising",isEnableFlashlightAdvertising);
        modelAndView.addObject("isEnableEastday",isEnableEastday);
        modelAndView.setViewName(ENABLE_WEATHER_PAGE);
        return modelAndView;
    }

    @RequestMapping("/is-enable-wt-json.do")
    public boolean isEnableWeatherJson()
    {
        return isEnableWeather;
    }

    @RequestMapping("/is-enable-flashlight-advertising-json.do")
    public boolean isEnableFlashlightAdvertisingJson()
    {
        return isEnableFlashlightAdvertising;
    }

    @RequestMapping("/is-enable-eastday-json.do")
    public boolean isEnableEastdayJson(){
        return isEnableEastday;
    }

    private static final String IS_ENABLE_WEATHER_DO = "redirect:/weather/is-enable-wt.do";
    @RequestMapping("/set-enable-wt.do")
    public ModelAndView setEnableWeather(boolean isEnable)
    {
        isEnableWeather = isEnable;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(IS_ENABLE_WEATHER_DO);
        return modelAndView;
    }

    @RequestMapping("/set-enable-flashlight-advertising.do")
    public ModelAndView setEnableFlashlightAdvertising(boolean isEnable)
    {
        isEnableFlashlightAdvertising = isEnable;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(IS_ENABLE_WEATHER_DO);
        return modelAndView;
    }


    private static boolean isEnableEastday = true;
    @RequestMapping("/set-enable-eastday.do")
    public ModelAndView setEnableEastday(boolean isEnable)
    {
        isEnableEastday = isEnable;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(IS_ENABLE_WEATHER_DO);
        return modelAndView;
    }



    @RequestMapping(value = "/fetch-weather-of-seven-day.do",method = RequestMethod.POST)
    public Map fetchWeatherOfSevenDay(String cityID,String html,String securityKey)
    {
        if (SystemConfigParameter.SECURITY_KEY().equals(securityKey)) {
            if (html != null) {
                this.weatherService.setWeatherOfSevenDay(cityID, html);
            }
            return this.weatherService.getSevenDayWeather(cityID);
        }
        else {
            return null;
        }
    }


    @RequestMapping(value = "/fetch-index-of-living.do",method = RequestMethod.POST)
    public Map fetchIndexOfLiving(String cityID,String html,String securityKey)
    {
        if (SystemConfigParameter.SECURITY_KEY().equals(securityKey)) {
            if (html != null) {
                this.weatherService.setIndexOfLiving(cityID, html);
            }
            return this.weatherService.getIndexOfLiving(cityID);
        }
        else {
            return null;
        }
    }

    @RequestMapping(value = "/fetch-weather-in-period.do",method = RequestMethod.POST)
    public Map fetchWeatherInPeriod(String cityID,String html,String securityKey)
    {
        if (SystemConfigParameter.SECURITY_KEY().equals(securityKey)) {
            if (html != null) {
                this.weatherService.setWeatherInPeriod(cityID, html);
            }
            return this.weatherService.getWeatherInPeriod(cityID);
        }
        else {
            return null;
        }
    }

    @RequestMapping(value = "/fetch-weather-for-aqi.do",method = RequestMethod.POST)
    public Map fetchAQI(String cityID,String html,String securityKey)
    {
        if (SystemConfigParameter.SECURITY_KEY().equals(securityKey)) {
            if (html != null) {
                this.weatherService.setWeatherForAQI(cityID, html);
            }
            return this.weatherService.getWeatherForAQI(cityID);
        }
        else {
            return null;
        }
    }

    @RequestMapping(value = "/fetch-weather-for-disaster-warning.do",method = RequestMethod.POST)
    public Map fetchDisasterWarning(String cityID,String html,String securityKey)
    {
        if (SystemConfigParameter.SECURITY_KEY().equals(securityKey))
        {
            return this.weatherService.getDisasterWarning(cityID,html);
        }
        else {
            return null;
        }
    }

}
