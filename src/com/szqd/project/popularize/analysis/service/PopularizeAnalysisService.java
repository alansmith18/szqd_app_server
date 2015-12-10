package com.szqd.project.popularize.analysis.service;

import com.szqd.project.popularize.analysis.model.AppActivationEntity;
import com.szqd.project.popularize.analysis.model.AppInfoEntity;
import com.szqd.project.popularize.analysis.model.PlatformUser;

import java.util.List;

/**
 * Created by like on 3/31/15.
 */
public interface PopularizeAnalysisService
{



    /**
     * 添加app激活信息
     * @param appActivationEntity
     */
    public void addAppActivationInfo(AppActivationEntity appActivationEntity);

    /**
     * 添加app信息
     * @param appInfoEntity
     */
    public void addAppInfo(AppInfoEntity appInfoEntity);

    /**
     * 注册平台
     * @param platformUser
     */
    public void addPlatform(PlatformUser platformUser);

    /**
     * 查询产品列表
     * @return
     */
    public List<AppInfoEntity> queryAllAppInfo();

    /**
     * 查询除admin以外的其他用户
     * @return
     */
    public List<PlatformUser> queryAllPlatformUsersExceptAdmin();

    /**
     * 根据条件查询单个platforUser
     * @param platformParam
     * @return
     */
    public PlatformUser queryPlateformUserByPlatformCondition(PlatformUser platformParam);

    /**
     * 通过相关条件,查询相关的app激活信息
     * @param platformUserCondition
     * @param appActivationCondition
     * @return
     */
    public List<AppActivationEntity> queryAppActivationInfoByPlatformIDList(PlatformUser platformUserCondition,AppActivationEntity appActivationCondition);

    /**
     * 更新PlatformUser
     * @param platformUserEdit
     */
    public void updatePlatformUser(PlatformUser platformUserEdit);

    /**
     * 保存platformuser
     * @param platformUser
     */
    public void savePlatformUser(PlatformUser platformUser);


    /**
     * 检查登录名是否重复
     * @param loginName
     * @return true:不重复. false:重复
     */
    public Boolean checkDuplicateLoginName(String loginName);


}
