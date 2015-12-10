package com.szqd.project.mobile.lock.service;


import com.szqd.framework.model.SelectEntity;
import com.szqd.project.mobile.lock.model.URLCategoryEntity;
import com.szqd.project.mobile.lock.model.json.JSONForAppAnalysisEntity;

import java.util.HashMap;
import java.util.List;


/**
 * Created by like on 3/24/15.
 */
public interface AppAnalysisService {

    /**
     * 保存app单次记录的实体
     * @param jsonObject
     */
    public void saveAppUseageInfoToCache(JSONForAppAnalysisEntity jsonObject);

    /**
     * 执行redis队列中得任务,将基础数据保存到数据库中
     */
    public void saveAppUseAgeInfoToDatabase();


    public Integer queryCategoryByAppName(String appName);

    /**
     * 查询用户分析中的app列表
     * @return
     */
    public List<SelectEntity> queryAppListWithAnalysis();

    /**
     * 根据单个app的id统计时长使用情况
     * @param appID
     * @return
     */
    public List<HashMap> countTimeUseageByAppID(String appID,Integer categoryID);

    /**
     * 根据app类别查询类别总得使用时长
     * @return
     */
    public List<HashMap> countTotalTimeUseage();


    /**
     * 根据单个app统计时段使用情况
     * @param appID
     * @return
     */
    public List<HashMap> countTimeByAppID(String appID,Integer categoryID);

    /**
     * 将客户端的json数据解析并且放入到缓存任务队列中
     * @param clientJson
     */
    public void saveAppFromClientJson(String clientJson);

    /**
     * 解析json数据到对象
     * @param json
     * @return
     */
    public JSONForAppAnalysisEntity parseJson(String json);

    /**
     * 根据单个app统计用户使用次数
     * @param appID
     * @param countMin
     * @param countMax
     * @return
     */
    public HashMap countFrequencyByAppID(String appID,Integer categoryID, Integer countMin,Integer countMax);

    /**
     * 通过用户id查找兴趣(app分类)
     * @param userID
     * @return
     */
    public Integer queryInterestByUserID(String userID);

    public List<URLCategoryEntity> getCategoryList();

    public void saveAppCategoryFrom360();

    public void checkNoCategoryApp();
}
