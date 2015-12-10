package com.szqd.project.mobile.lock.model.weather;

/**
 * Created by like on 9/29/15.
 */
public class SevenDayWeather {
    /**
     * 周几
     */
    private String weekDayText = null;

    /**
     * 日期
     */
    private String dateText = null;

    /**
     * 天气
     */
    private String weather = null;

    /**
     * 最高温度
     */
    private String maximumTemperature = null;

    /**
     * 最低温度
     */
    private String lowestTemperature = null;

    /**
     * 风力
     */
    private String windPower = null;


    public String getWeekDayText() {
        return weekDayText;
    }

    public void setWeekDayText(String weekDayText) {
        this.weekDayText = weekDayText;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(String maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(String lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }
}
