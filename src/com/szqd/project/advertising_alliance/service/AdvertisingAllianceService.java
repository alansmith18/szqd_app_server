package com.szqd.project.advertising_alliance.service;

import com.szqd.framework.model.Pager;
import com.szqd.project.advertising_alliance.model.AdvertisingPOJO;
import com.szqd.project.advertising_alliance.pojo.ActivationPOJO;
import com.szqd.project.popularize.analysis.model.PlatformUser;

import java.util.List;
import java.util.Set;

/**
 * Created by like on 10/26/15.
 */
public interface AdvertisingAllianceService
{

    /**
     * 通过id从数据库获取广告
     * @param id
     * @return
     */
    public AdvertisingPOJO fetchAdvertisingByIDFromDB(Long id);

    /**
     * 提交广告
     * @param advertising
     */
    public void submitAdvertising(AdvertisingPOJO advertising);

    /**
     * 根据条件分页罗列广告
     * @param pager
     * @param queryCondition
     * @param fields
     * @return
     */
    public List<AdvertisingPOJO> listAdvertising(Pager pager,AdvertisingPOJO queryCondition,String ...fields);



    public List<PlatformUser> queryUsersWithIDs(Set<Long> ids);



    public void updateActivation(ActivationPOJO activation);

    /**
     * 保存或更新PlatformUser
     * @param platformUserEdit
     */
    public void upsetPlatformUser(PlatformUser platformUserEdit);

    public ActivationPOJO queryActivationWithDate(ActivationPOJO condition);

}
