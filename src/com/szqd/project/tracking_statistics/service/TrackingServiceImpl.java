package com.szqd.project.tracking_statistics.service;


import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.BeanUtil;
import com.szqd.project.tracking_statistics.model.TrackingDataDB;
import com.szqd.project.tracking_statistics.model.TrackingDataPOJO;
import com.szqd.project.tracking_statistics.model.TrackingEventPOJO;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by like on 11/18/15.
 */
public class TrackingServiceImpl extends SuperService implements TrackingService
{
    private static final Logger LOGGER = Logger.getLogger(TrackingServiceImpl.class);

    public List<TrackingEventPOJO> listEvent(TrackingEventPOJO condition,Pager pager)
    {
        Query query = new Query();
        Criteria where = Criteria.where("");
        String nameRegex = condition.getNameRegex();
        if (nameRegex != null) where = where.and("name").regex(nameRegex);

        Long projectID = condition.getProjectID();
        if (projectID != null) where = where.and("projectID").is(projectID);

        query.addCriteria(where);
        if (pager != null)
        {
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());
        }
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        return mongoTemplate.find(query,TrackingEventPOJO.class,TrackingEventPOJO.ENTITY_NAME);
    }

    public TrackingEventPOJO queryTrackingEventByIDFromDB(Long id)
    {
        Query query = new Query(Criteria.where("_id").is(id));

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        return mongoTemplate.findOne(query, TrackingEventPOJO.class, TrackingEventPOJO.ENTITY_NAME);
    }


    private static final String TRACKING_EVENT_CACHE_HASH_KEY = "TRACKING_EVENT_CACHE_HASH_KEY";
    public TrackingEventPOJO queryTrackingEventByIDFromCache(Long id)
    {
        TrackingEventPOJO event = null;
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundHashOperations hashOps = redisTemplate.boundHashOps(TRACKING_EVENT_CACHE_HASH_KEY);
        Object eventCacheObj = hashOps.get(String.valueOf(id));
        if (eventCacheObj == null)
        {
            event = this.queryTrackingEventByIDFromDB(id);
            LOGGER.debug("读取事件:数据库");
            hashOps.put(String.valueOf(id),event);
            LOGGER.debug("保存事件:缓存");
        }
        else {
            event = new BeanUtil<TrackingEventPOJO>().convertOneMapListToOneBean((Map)eventCacheObj,TrackingEventPOJO.class);
            LOGGER.debug("读取事件:缓存");
        }

        return event;
    }

    public void upsertTrackingEvent(TrackingEventPOJO edit)
    {
        Long id = edit.getId();
        if (id == null) id = this.getNextSequenceForFieldKey(TrackingEventPOJO.ENTITY_NAME);
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.setOnInsert("_id",id);
        update.set("name",edit.getName());
        update.set("forwardURL",edit.getForwardURL());
        update.set("projectID",edit.getProjectID());

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        mongoTemplate.upsert(query,update,TrackingEventPOJO.ENTITY_NAME);

        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        redisTemplate.boundHashOps(TRACKING_EVENT_CACHE_HASH_KEY).delete(String.valueOf(id));

    }

    public void addTracking(TrackingDataDB tracking)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        mongoTemplate.insert(tracking,TrackingDataDB.ENTITY_NAME);
    }

    public List<TrackingDataPOJO> fetchTrackingForReport(TrackingDataPOJO query)
    {
        List<TrackingDataPOJO> list = new ArrayList<>();
        if (query != null)
        {
            List<Long> dateList = query.getDateListBetweenBeginToEnd();
            if (dateList != null)
            {
                for (Long dateVar : dateList)
                {
                    TrackingDataPOJO data = this.fetchTrackingFromDB(dateVar,query);
                    list.add(data);
                }
            }
//            Criteria beginDay = Criteria.where("createDay").gte();
//            Criteria endDay = Criteria.where("createDay").lte(query.getEndDay());
//            Criteria where = new Criteria().andOperator(beginDay,endDay);
        }

        return list;
    }

    public TrackingDataPOJO fetchTrackingFromDB(long createDay,TrackingDataPOJO query)
    {
        Criteria where = Criteria.where("eventID").is(query.getEventID());
        where = where.and("createDay").is(createDay);

//        MatchOperation match = Aggregation.match(where);
//        ProjectionOperation project = Aggregation.project("createTime","createDay");
//        GroupOperation group = Aggregation.group("createDay").count().as("numberOfClick").first("createTime").as("createTime");
//        Aggregation aggregation = Aggregation.newAggregation(match,project,group);

        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        long count = mongoTemplate.count(new Query(where),TrackingDataPOJO.ENTITY_NAME);
        TrackingDataPOJO data = new TrackingDataPOJO();
        data.setNumberOfClick(count);
        data.setCreateTime(createDay);
        return data;

//        List<TrackingDataPOJO> list = mongoTemplate.aggregate(aggregation, TrackingDataPOJO.ENTITY_NAME,TrackingDataPOJO.class).getMappedResults();
//        if (list.isEmpty())
//        {
//            TrackingDataPOJO data = new TrackingDataPOJO();
//            data.setNumberOfClick(0l);
//            data.setCreateTime(createDay);
//            return data;
//        }
//        return list.get(0);
    }

}
