package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.common.model.AdvertisingEntityDB;
import com.szqd.project.common.model.AdvertisingEntityPojo;

import java.util.List;

/**
 * Created by like on 5/19/15.
 */
public interface AdvertisingService {

    /**
     * 保存广告
     * @param advertising
     */
    public void saveAdvertising(AdvertisingEntityDB advertising);

    /**
     * 显示广告列表
     * @param advertising
     * @return
     */
    public List<AdvertisingEntityPojo> listAdvertising(AdvertisingEntityDB advertising,Pager pager);


    /**
     * 通过项目id查找今天所属的广告
     * @param condition
     * @return
     */
    public List<AdvertisingEntityPojo> fetchTodayAdvertisingListByProjectID(AdvertisingEntityPojo condition);

    /**
     * 数据库:根据id获取广告内容
     * @param id
     * @return
     */
    public AdvertisingEntityPojo loadAdvertisingByIDFromDB(Long id);
}
