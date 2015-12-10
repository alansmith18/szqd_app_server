package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.VersionEntity;
import com.szqd.project.common.model.VersionPOJO;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by like on 4/21/15.
 */
public class VersionServiceImpl extends SuperService implements VersionService
{
    private static final Logger logger = Logger.getLogger(VersionServiceImpl.class);

    /**
     * 获取所有的版本更新
     * @return
     */
    public List<VersionPOJO> getVersionListByCondition(VersionPOJO condition,Pager pager)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("");
            String appNameRegex = condition.getAppNameRegex();
            if (appNameRegex != null) criteria = criteria.and("appName").regex(appNameRegex);

            String channelNo = condition.getChannelNo();
            if (channelNo != null) criteria = criteria.and("channelNo").is(channelNo);

            String versionNo = condition.getVersionNo();
            if (versionNo != null) criteria = criteria.and("versionNo").is(versionNo);
            query.addCriteria(criteria);

            if(pager != null)
            {
                query.skip(pager.getOffset());
                query.limit(pager.getCapacity());
            }

            List<VersionPOJO> list = mongoTemplate.find(query, VersionPOJO.class, VersionPOJO.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.VersionServiceImpl.getAllVersionList()",e);
            throw new RuntimeException("获取版本列表出错");
        }
    }

    public VersionPOJO findVersionInfoByID(Long id)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(id);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query,VersionPOJO.class,VersionPOJO.ENTITY_NAME);
    }

    /**
     * 检查app版本
     * @return
     */
    public VersionPOJO findVersionInfo(VersionPOJO condition)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("");

            String appName = condition.getAppName();
            if (appName != null) criteria = criteria.and("appName").is(appName);

            String channelNo = condition.getChannelNo();
            if (channelNo != null) criteria = criteria.and("channelNo").is(channelNo);

            String versionNo = condition.getVersionNo();
            if (versionNo != null) criteria = criteria.and("versionNo").is(versionNo);
            query.addCriteria(criteria);

            VersionPOJO versionEntity = mongoTemplate.findOne(query,VersionPOJO.class,VersionPOJO.ENTITY_NAME);

            return versionEntity;
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.VersionServiceImpl.checkAndFindVersion(Integer appID,Integer versionNo)",e);
            throw new RuntimeException("检查app版本出错");
        }

    }

    /**
     * 编辑或者新增app版本
     * @param newVersion
     */
    public void saveOrUpdateVersion(VersionEntity newVersion)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Long id = newVersion.getId();
            if (id == null) id = this.getNextSequenceForFieldKey(VersionEntity.ENTITY_NAME);

            query.addCriteria(Criteria.where("id").is(id));
            Update update = new Update();
            update.setOnInsert("id",id);
            update.set("appName",newVersion.getAppName());
            update.set("url",newVersion.getUrl());
            update.set("versionNo",newVersion.getVersionNo());
            update.set("channelNo",newVersion.getChannelNo());
            update.set("status",newVersion.getStatus());
            mongoTemplate.upsert(query,update,VersionEntity.class,VersionEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.VersionServiceImpl.saveOrUpdateVersion(VersionEntity newVersion,VersionEntity queryCondition)",e);
            throw new RuntimeException("编辑或者新增app版本出错");
        }
    }

}
