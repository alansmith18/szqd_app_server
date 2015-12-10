package com.szqd.project.mobile.lock.model.json.weather;

import java.util.List;

/**
 * Created by like on 4/7/15.
 */
public class AllWeather
{
    private String cityID;
    private String cityChineseName;
    private List<WeatherEntity> weekWeather;

    private String disasterDetail;
    private String disasterLevel;
    private String disasterTitle;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityChineseName() {
        return cityChineseName;
    }

    public void setCityChineseName(String cityChineseName) {
        this.cityChineseName = cityChineseName;
    }

    public List<WeatherEntity> getWeekWeather() {
        return weekWeather;
    }

    public void setWeekWeather(List<WeatherEntity> weekWeather) {
        this.weekWeather = weekWeather;
    }

    public String getDisasterDetail() {
        return disasterDetail;
    }

    public void setDisasterDetail(String disasterDetail) {
        this.disasterDetail = disasterDetail;
    }

    public String getDisasterLevel() {
        return disasterLevel;
    }

    public void setDisasterLevel(String disasterLevel) {
        this.disasterLevel = disasterLevel;
    }

    public String getDisasterTitle() {
        return disasterTitle;
    }

    public void setDisasterTitle(String disasterTitle) {
        this.disasterTitle = disasterTitle;
    }
}
