package com.szqd.project.mobile.lock.model.json.weather;

/**
 * Created by like on 15/3/18.
 */
public class WeatherEntity
{

    private String weekDayText;
    private String weatherText;
    private String temperature;
    private String pmLevel;
    private String realtimeTemp = null;


    public String getPmLevel() {
        return pmLevel;
    }

    public void setPmLevel(String pmLevel) {
        this.pmLevel = pmLevel;
    }

    public String getRealtimeTemp() {
        return realtimeTemp;
    }

    public void setRealtimeTemp(String realtimeTemp) {
        this.realtimeTemp = realtimeTemp;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    public String getWeekDayText() {
        return weekDayText;
    }

    public void setWeekDayText(String weekDayText) {
        this.weekDayText = weekDayText;
    }
}
