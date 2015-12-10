package com.szqd.project.mobile.lock.service;

import com.szqd.project.mobile.lock.model.json.weather.AllWeather;

import java.util.List;
import java.util.Map;

/**
 * Created by like on 15/3/18.
 */
public interface WeatherService {

    /**
     * 将客户端json数据保存到缓存
     * @param jsonString
     * @param sourceType
     */
    public void addWeatherToDb(String jsonString ,Integer sourceType);

    /**
     * 通过redis获取缓存天气数据
     * @param cityID
     * @return
     */
    public AllWeather getAllWeatherFromRedis(String cityID);


    /**
     * 获取七天天气,如果没有天气,则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getSevenDayWeather(String cityID);

    /**
     * 设置七天天气
     * @param cityID
     * @param html
     */
    public void setWeatherOfSevenDay(String cityID, String html);

    /**
     * 获取生活指数,如果没有, 则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getIndexOfLiving(String cityID);

    /**
     * 设置生活指数
     * @param cityID
     * @param html
     */
    public void setIndexOfLiving(String cityID, String html);

    /**
     * 获取小时区间天气,如果没有则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getWeatherInPeriod(String cityID);

    /**
     * 设置小时区间天气
     * @param cityID
     * @param html
     */
    public void setWeatherInPeriod(String cityID,String html);

    /**
     * 获取AQI,如果没有则返回需要抓取的任务
     * @param cityID
     * @return
     */
    public Map<String,Object> getWeatherForAQI(String cityID);

    /**
     * 设置AQI
     * @param cityID
     * @param html
     */
    public void setWeatherForAQI(String cityID,String html);


    /**
     * 获取灾害预警
     * @param cityID
     * @param html
     * @param urlList
     * @return
     */
    public Map<String,Object> getDisasterWarning(String cityID,String html);


}
