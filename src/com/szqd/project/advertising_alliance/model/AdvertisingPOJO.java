package com.szqd.project.advertising_alliance.model;

import com.szqd.framework.model.SelectEntity;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by like on 10/27/15.
 */
public class AdvertisingPOJO extends AdvertisingDB {

    private String regexName = null;

    public String getRegexName() {
        return regexName;
    }

    public void setRegexName(String regexName) {
        this.regexName = regexName;
    }


    public void setIsAccept(Boolean isAccept) {
        if (isAccept) this.setStatus(STATUS_IN_USE);
        else this.setStatus(STATUS_FAILED);
    }

    public String getStatusText()
    {
        String statusText = null;
        if (STATUS_SUBMIT == this.getStatus()) statusText = "已提交";
        else if (STATUS_IN_USE == this.getStatus()) statusText = "使用中";
        else if (STATUS_FAILED == this.getStatus()) statusText = "未通过";
        else if (STATUS_STOP == this.getStatus()) statusText = "已停止";

        return statusText;
    }

    private List<SelectEntity> pendingChannelTextList;

    public List<SelectEntity> getPendingChannelTextList() {
        return pendingChannelTextList;
    }

    public void setPendingChannelTextList(List<PlatformUser> platformUserList) {
        pendingChannelTextList = new ArrayList<>();
        if (platformUserList != null)
        {
            for (PlatformUser u : platformUserList) {
                SelectEntity s = new SelectEntity();
                s.setText(u.getPlatformName());
                s.setValue(String.valueOf(u.getId()));
                pendingChannelTextList.add(s);
            }
        }
    }

    @Transient
    private Long numberOfActivation = null;

    public Long getNumberOfActivation() {
        return numberOfActivation;
    }

    public void setNumberOfActivation(Long numberOfActivation) {
        this.numberOfActivation = numberOfActivation;
    }

    private Long totalActivation = null;

    public Long getTotalActivation() {
        return totalActivation;
    }

    public void setTotalActivation(Long totalActivation) {
        this.totalActivation = totalActivation;
    }

    public Integer getPendingSize()
    {
        Set list= this.getPendingChannelIDList();
        if (list == null) return 0;
        else return list.size();
    }
}
