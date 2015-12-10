package com.szqd.project.popularize.analysis.service;


import com.szqd.framework.service.SuperService;
import com.szqd.project.popularize.analysis.model.AppActivationEntity;
import com.szqd.project.popularize.analysis.model.AppInfoEntity;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like on 3/31/15.
 */
public class PopularizeAnalysisServiceImpl extends SuperService implements PopularizeAnalysisService
{
    private static final Logger logger = Logger.getLogger(PopularizeAnalysisServiceImpl.class);

    /**
     * 添加app激活信息
     * @param appActivationEntity
     */
    public void addAppActivationInfo(AppActivationEntity appActivationEntity)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(appActivationEntity,AppActivationEntity.ENTITY_NAME);

        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.addAppActivationInfo(AppActivationEntity appActivationEntity)",e);
            throw new RuntimeException("添加app激活信息失败");
        }

    }

    /**
     * 添加app信息
     * @param appInfoEntity
     */
    public void addAppInfo(AppInfoEntity appInfoEntity)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            List<AppInfoEntity> listExists = mongoTemplate.findAll(AppInfoEntity.class, AppInfoEntity.ENTITY_NAME);

            for (int i = 0; i < listExists.size(); i++)
            {
                AppInfoEntity appInfoExistsVar = listExists.get(i);
                boolean isSameAppInfo = appInfoExistsVar.getAppID().equals(appInfoEntity.getAppID());
                if (isSameAppInfo)
                {
                    return;
                }
            }

            mongoTemplate.insert(appInfoEntity,AppInfoEntity.ENTITY_NAME);

        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.addAppInfo(AppInfoEntity appInfoEntity)",e);
            throw new RuntimeException("添加app信息失败");
        }
    }

    /**
     * 注册平台
     * @param platformUser
     */
    public void addPlatform(PlatformUser platformUser)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(platformUser,PlatformUser.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.addPlatform(PlatformUser platformUser)",e);
            throw new RuntimeException("注册平台失败");
        }

    }

    /**
     * 查询产品列表
     * @return
     */
    public List<AppInfoEntity> queryAllAppInfo()
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            List<AppInfoEntity> appList = mongoTemplate.findAll(AppInfoEntity.class, AppInfoEntity.ENTITY_NAME);
            return appList;
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.queryAppInfo()",e);
            throw new RuntimeException("查询app产品列表失败");
        }
    }

    /**
     * 查询除admin以外的其他用户
     * @return
     */
    public List<PlatformUser> queryAllPlatformUsersExceptAdmin()
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            String queryString = "{loginName:{$ne:'admin'}}";
            String fieldsString = "{_id:1,platformID:1,platformName:1,loginName:1,loginPwd:1,incremental:1,scale:1}";
            BasicQuery query = new BasicQuery(queryString,fieldsString);
            List<PlatformUser> platformUsers = mongoTemplate.find(query, PlatformUser.class, PlatformUser.ENTITY_NAME);

            return platformUsers;
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.queryAllPlatformUsers()",e);
            throw new RuntimeException("查询所有供应商列表失败");
        }
    }

    /**
     * 根据条件查询单个platforUser
     * @param platformParam
     * @return
     */
    public PlatformUser queryPlateformUserByPlatformCondition(PlatformUser platformParam)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();

            if (platformParam != null && platformParam.getId() != null)
            {
                query.addCriteria(Criteria.where("id").is(platformParam.getId()));
            }
            else
            {
                return null;
            }

            List<PlatformUser> platformUserList = mongoTemplate.find(query,PlatformUser.class,PlatformUser.ENTITY_NAME);
            boolean isCorrect = platformUserList != null && platformUserList.size() == 1;
            if (isCorrect)
            {
                return platformUserList.get(0);
            }
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.queryPlateformUserByPlatformCondition(PlatformUser platformParam)",e);
        }

        return null;
    }

    /**
     * 通过相关条件,查询相关的app激活信息
     * @param platformUserCondition
     * @param appActivationCondition
     * @return
     */
    public List<AppActivationEntity> queryAppActivationInfoByPlatformIDList(PlatformUser platformUserCondition,AppActivationEntity appActivationCondition)
    {
        List<AppActivationEntity> list = null;
        try
        {
            Long platformID = platformUserCondition.getPlatformID();

            Criteria condition = null;
            //必要查询条件是platformID
            if (platformID != null)
            {
                condition = Criteria.where("platformID").is(platformID.intValue());
            }

            //根据appID动态查询
            String appID = appActivationCondition.getAppID();
            if (appID != null)
            {
                if (condition == null)
                {
                    condition = Criteria.where("appID").is(appID);
                }
                else {
                    condition.and("appID").is(appID);
                }
            }


            List<Criteria> dateConditionQueryList = new ArrayList();
            Long beginDateLongType = appActivationCondition.getBeginDateLongType();
            if (beginDateLongType != null)
            {
                dateConditionQueryList.add(Criteria.where("createTime").gte(beginDateLongType));
            }

            Long endDateLongType = appActivationCondition.getEndDateLongType();
            if (endDateLongType != null)
            {
                dateConditionQueryList.add(Criteria.where("createTime").lte(endDateLongType));
            }

            Criteria[] criteriasForDate = new Criteria[dateConditionQueryList.size()];
            for (int i = 0 ; i < dateConditionQueryList.size(); i++)
            {
                criteriasForDate[i] = dateConditionQueryList.get(i);
            }
            if (condition == null && criteriasForDate.length > 0)
            {
                condition = Criteria.where("").andOperator(criteriasForDate);
            }
            else if ( criteriasForDate.length > 0)
            {
                condition.andOperator(criteriasForDate);
            }

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            GroupOperation groupOperation = Aggregation.group("platformID","appID").first("appName").as("appName").count().as("userCount");
            ProjectionOperation projectionOperation = Aggregation.project("userCount", "appName", "platformID", "appID");//.and("_id").as("appID");

            MatchOperation matchOperation = null;
            if (condition != null)
            {
                matchOperation = new MatchOperation(condition);
            }

            List<AggregationOperation> aggregationOperationList = new ArrayList<AggregationOperation>();
            if (matchOperation != null)
            {
                aggregationOperationList.add(matchOperation);
            }
            aggregationOperationList.add(groupOperation);
            aggregationOperationList.add(projectionOperation);

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperationList);
            AggregationResults<AppActivationEntity> aggregationResults = mongoTemplate.aggregate(aggregation, AppActivationEntity.ENTITY_NAME, AppActivationEntity.class);
            list = aggregationResults.getMappedResults();

        } catch (Exception e)
        {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.queryAppActivationInfoByPlatformIDList(PlatformUser platformUserCondition,AppActivationEntity appActivationCondition)",e);
            throw new RuntimeException("查询app激活数据出错");
        }
        return list;
    }


    /**
     * 更新PlatformUser
     * @param platformUserEdit
     */
    public void updatePlatformUser(PlatformUser platformUserEdit)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{}";
            String fields = "{}";
            BasicQuery query = new BasicQuery(queryString,fields);
            query.addCriteria(Criteria.where("_id").is(platformUserEdit.getId()));

            Update update = new Update();
            update.set("platformName",platformUserEdit.getPlatformName());
            update.set("loginName",platformUserEdit.getLoginName());
            update.set("loginPwd",platformUserEdit.getLoginPwd());
            update.set("incremental",platformUserEdit.getIncremental());
            update.set("scale",platformUserEdit.getScale());
            update.set("role",platformUserEdit.getRole());
            mongoTemplate.upsert(query, update, PlatformUser.class, PlatformUser.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.updatePlatformUser(PlatformUser platformUserEdit)",e);
            throw new RuntimeException("更新PlatformUser出错");
        }
    }

    /**
     * 保存platformuser
     * @param platformUser
     */
    public void savePlatformUser(PlatformUser platformUser)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            boolean isPlatformNameNotNull = platformUser.getPlatformName() != null;
            if (isPlatformNameNotNull)
            {
                Long platformID = this.getNextSequenceForFieldKey(PlatformUser.ENTITY_NAME+":platformID");
                platformUser.setPlatformID(platformID);
            }

            Long userID = this.getNextSequenceForFieldKey(PlatformUser.ENTITY_NAME+":privateKey");
            platformUser.setId(userID);
            mongoTemplate.insert(platformUser, PlatformUser.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.savePlatformUser(PlatformUser platformUser)",e);
            throw new RuntimeException("保存platformuser出错");
        }

    }

    /**
     * 检查登录名是否重复
     * @param loginName
     * @return true:不重复. false:重复
     */
    public Boolean checkDuplicateLoginName(String loginName)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{loginName:'%s'}";
            queryString = String.format(queryString,loginName);
            String fields = "{_id:0}";
            BasicQuery query = new BasicQuery(queryString,fields);
            Long count = mongoTemplate.count(query,PlatformUser.class,PlatformUser.ENTITY_NAME);
            if (count != null && count == 0)
            {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("com.szqd.project.popularize.analysis.service.PopularizeAnalysisServiceImpl.checkDuplicateLoginName(String loginName)",e);
            throw new RuntimeException("检查登录名是否重复出错");
        }
    }

}
