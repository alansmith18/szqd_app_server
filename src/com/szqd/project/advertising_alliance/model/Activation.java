package com.szqd.project.advertising_alliance.model;

import java.io.Serializable;

/**
 * Created by like on 12/9/15.
 */
public class Activation
{
    public static final String ENTITY_NAME = "AD_ALLIANCE_ACTIVATION";

    private Long adID;
    private Long channelID;
    private Long numberOfActivation;
    private Long date;

    public Activation() {
    }

    public Long getAdID() {
        return adID;
    }

    public void setAdID(Long adID) {
        this.adID = adID;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getChannelID() {
        return channelID;
    }

    public void setChannelID(Long channelID) {
        this.channelID = channelID;
    }

    public Long getNumberOfActivation() {
        return numberOfActivation;
    }

    public void setNumberOfActivation(Long numberOfActivation) {
        this.numberOfActivation = numberOfActivation;
    }
}
