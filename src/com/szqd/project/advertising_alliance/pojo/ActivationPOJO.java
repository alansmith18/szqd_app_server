package com.szqd.project.advertising_alliance.pojo;

import com.szqd.framework.util.DateUtils;
import com.szqd.project.advertising_alliance.model.Activation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by like on 12/10/15.
 */
public class ActivationPOJO extends Activation {

    public void setDateText(Date dateText)
    {
        if (dateText != null) {
            this.setDate(dateText.getTime());
        }
    }

    public String getDateText()
    {
        String dateText = null;
        if (this.getDate() != null) {
            dateText = DateUtils.dateToString(new Date(this.getDate()),"yyyy-MM-dd");
        }
        return dateText;
    }
}
