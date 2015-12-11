package com.szqd.project.advertising_alliance.service;

import com.mongodb.WriteResult;
import com.szqd.framework.model.Pager;
import com.szqd.framework.security.UsersRole;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.advertising_alliance.model.Activation;
import com.szqd.project.advertising_alliance.model.ActivationStatisticsByDayDB;
import com.szqd.project.advertising_alliance.model.AdvertisingDB;
import com.szqd.project.advertising_alliance.model.AdvertisingPOJO;
import com.szqd.project.advertising_alliance.pojo.ActivationPOJO;
import com.szqd.project.popularize.analysis.model.PlatformUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by like on 10/23/15.
 */
public class AdvertisingAllianceServiceImpl extends SuperService implements AdvertisingAllianceService {


    public ActivationPOJO queryActivationWithDate(ActivationPOJO condition)
    {

        Criteria where = new Criteria();
        where = where.and("date").is(condition.getDate());
        where = where.and("channelID").is(condition.getChannelID());
        where = where.and("adID").is(condition.getAdID());
        Query query = new Query(where);
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        ActivationPOJO activation = mongoTemplate.findOne(query,ActivationPOJO.class,ActivationPOJO.ENTITY_NAME);
        return activation;

    }



    public List<PlatformUser> queryUsersWithIDs(Set<Long> ids)
    {
        List<PlatformUser> platformUsers = null;
        if (ids != null)
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("_id").in(ids);
            query.fields().include("_id");
            query.fields().include("platformName");
            query.addCriteria(criteria);
            platformUsers = mongoTemplate.find(query, PlatformUser.class, PlatformUser.ENTITY_NAME);
        }

        return platformUsers;

    }


    /**
     * 根据条件分页罗列广告
     * @param pager
     * @param queryCondition
     * @param fields
     * @return
     */
    public List<AdvertisingPOJO> listAdvertising(Pager pager,AdvertisingPOJO queryCondition,String ...fields)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Query query = new Query();
        Criteria criteria = Criteria.where("");
        Integer status = queryCondition.getStatus();
        if (status != null)
        {
            criteria = criteria.and("status").is(status);
        }

        String regexName = queryCondition.getRegexName();
        if (regexName != null)
        {
            criteria = criteria.and("name").regex(regexName);
        }

        Long uid = queryCondition.getUid();
        if (uid != null)
        {
            criteria = criteria.and("uid").is(uid);
        }

        query.skip(pager.getOffset());
        query.limit(pager.getCapacity());
        query.addCriteria(criteria);
        query.with(new Sort(Sort.Direction.DESC,"createTime"));
        if (fields != null)
        {
            for (String field : fields) {
                query.fields().include(field);
            }
        }
        List<AdvertisingPOJO> list = mongoTemplate.find(query,AdvertisingPOJO.class,AdvertisingPOJO.ENTITY_NAME);
        return list;

    }

    /**
     * 通过id从数据库获取广告
     * @param id
     * @return
     */
    public AdvertisingPOJO fetchAdvertisingByIDFromDB(Long id)
    {
        if (id == null) return null;

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)),AdvertisingPOJO.class,AdvertisingPOJO.ENTITY_NAME);
    }



    public void updateActivation(ActivationPOJO activation)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Query query = new Query(Criteria.where("adID").is(activation.getAdID()).and("channelID").is(activation.getChannelID()).and("date").is(activation.getDate()));
        Update update = new Update();
        update.set("numberOfActivation",activation.getNumberOfActivation());
        mongoTemplate.upsert(query,update,Activation.ENTITY_NAME);
    }

    /**
     * 提交广告
     * @param advertising
     */
    public void submitAdvertising(AdvertisingPOJO advertising)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Long id = advertising.getId();
        if (id == null)
        {
            id = this.getNextSequenceForFieldKey(AdvertisingDB.ENTITY_NAME);
        }

        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.setOnInsert("_id", id);
        if (advertising.getName() != null) {
            update.set("name",advertising.getName());
        }
        if (advertising.getUrl() != null) {
            update.set("url", advertising.getUrl());
        }
        if (advertising.getDesc() != null) {
            update.set("desc", advertising.getDesc());
        }
        if (advertising.getPic() != null) {
            update.set("pic",advertising.getPic());
        }
        if (advertising.getUid() != null) {
            update.set("uid",advertising.getUid());
        }
        if (advertising.getWidth() != null) {
            update.set("width",advertising.getWidth());
        }
        if (advertising.getHeight() != null) {
            update.set("height",advertising.getHeight());
        }
        if (advertising.getStatus() != null) {
            update.set("status",advertising.getStatus());
        }
        if (advertising.getChannelIDList() != null)
        {
            update.set("channelIDList",advertising.getChannelIDList());
        }
//        if (advertising.getPendingChannelIDList() != null)
//        {
            update.set("pendingChannelIDList",advertising.getPendingChannelIDList());
//        }

        update.setOnInsert("createTime", Calendar.getInstance().getTimeInMillis());
        mongoTemplate.upsert(query,update,AdvertisingDB.ENTITY_NAME);
    }





    /**
     * 保存或更新PlatformUser
     * @param platformUserEdit
     */
    public void upsetPlatformUser(PlatformUser platformUserEdit)
    {

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Long id = platformUserEdit.getId();
        if (id == null) id = this.getNextSequenceForFieldKey(PlatformUser.ENTITY_NAME);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        Update update = new Update();
        update.setOnInsert("_id",id);
        update.set("platformName",platformUserEdit.getPlatformName());
        update.set("loginName",platformUserEdit.getLoginName());
        update.set("loginPwd",platformUserEdit.getLoginPwd());
        update.set("role",platformUserEdit.getRole());
        update.set("contactInfo",platformUserEdit.getContactInfo());
        mongoTemplate.upsert(query, update, PlatformUser.class, PlatformUser.ENTITY_NAME);
    }



}
