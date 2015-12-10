package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.FeedbackEntity;
import com.szqd.project.common.model.FeedbackPojo;

import java.util.List;
import java.util.Map;

/**
 * Created by like on 4/20/15.
 */
public interface FeedbackService
{
    /**
     * 保存反馈信息
     * @param feedback
     */
    public void saveFeedback(FeedbackEntity feedback);

    /**
     * 分页查找反馈信息
     * @param pager
     * @param queryCondition
     * @param beginDateString
     * @param endDateString
     * @return
     */
    public List<FeedbackPojo> findFeedbackListByPage(Pager pager,FeedbackEntity queryCondition,String beginDateString,String endDateString);

//    /**
//     * 添加卸载次数
//     * @param appName
//     */
//    public void addUninstallTotalCount(String appName);

    /**
     * 添加每天的卸载次数
     * @param appName
     */
    public void addUninstallTodayCount(String appName);

    /**
     *
     * @param appName
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<Map> fetchUninstall(String appName,Long beginDate,Long endDate);

}
