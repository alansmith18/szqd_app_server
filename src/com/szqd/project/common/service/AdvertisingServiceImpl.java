package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.BeanUtil;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.common.model.AdvertisingEntityDB;
import com.szqd.project.common.model.AdvertisingEntityPojo;
import com.szqd.project.common.model.AdvertisingTypeEnum;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by like on 5/19/15.
 */
public class AdvertisingServiceImpl extends SuperService implements AdvertisingService {

    private static final Logger LOGGER = Logger.getLogger(AdvertisingServiceImpl.class);

    /**
     * 显示广告列表
     * @param advertising
     * @return
     */
    public List<AdvertisingEntityPojo> listAdvertising(AdvertisingEntityDB advertising,Pager pager)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = new Criteria();

            String advertisingName = advertising.getName();
            if (advertisingName != null)
            {
                criteria.and("name").regex(advertisingName);
            }

            List<Integer> projects = advertising.getProjects();
            if (projects != null)
            {
                criteria.and("projects").in(projects);
            }

            List<Integer> platform = advertising.getPlatform();
            if (platform != null)
            {
                criteria.and("platform").in(platform);
            }

            query.addCriteria(criteria);
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());

            List<AdvertisingEntityPojo> list = mongoTemplate.find(query,AdvertisingEntityPojo.class,AdvertisingEntityDB.ENTITY_NAME);
            return list;

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.listAdvertising(AdvertisingEntityDB advertising)",e);
            throw new RuntimeException("显示广告列表出错");
        }
    }

    /**
     * 保存广告
     * @param advertising
     */
    public void saveAdvertising(AdvertisingEntityDB advertising)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Long id = advertising.getId();
            if (id == null)
            {
                id = this.getNextSequenceForFieldKey(AdvertisingEntityDB.ENTITY_NAME);
            }
            else{
                RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
                redisTemplate.boundHashOps(CACHE_KEY_FOR_ADVERTISING_CONTENT_BY_PROJECT_ID).delete(id.toString());
            }
            Query query = Query.query(Criteria.where("id").is(id));
            Update update = new Update();
            update.setOnInsert("id", id);
            update.set("name", advertising.getName());
            update.set("type",advertising.getType());
            update.set("projects",advertising.getProjects());
            update.set("targetURL",advertising.getTargetURL());
            update.set("picPath",advertising.getPicPath());
            update.set("platform",advertising.getPlatform());
            update.set("duration",advertising.getDuration());
            update.set("effectiveStartTime",advertising.getEffectiveStartTime());
            update.set("effectiveEndTime", advertising.getEffectiveEndTime());
            update.set("frameType",advertising.getFrameType());
            update.set("clickType",advertising.getClickType());
            update.set("playTime", advertising.getPlayTime());
            update.set("networkType", advertising.getNetworkType());
            update.set("packageName",advertising.getPackageName());
            update.set("desc",advertising.getDesc());
            mongoTemplate.upsert(query, update, AdvertisingEntityDB.class, AdvertisingEntityDB.ENTITY_NAME);

            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            redisTemplate.delete(CACHE_KEY_FOR_ADVERTISING_CONTENT_BY_PROJECT_ID);

            String redisKey = CACHE_KEY_FOR_ADVERTISING_ID_LIST_BY_PROJECT_ID_WITH_TYPE_ID + advertising.getType().toString();
            redisTemplate.delete(redisKey);

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.saveAdvertising(AdvertisingEntityDB advertising)",e);
            throw new RuntimeException("保存广告出错");
        }
    }



    private static final String CACHE_KEY_FOR_ADVERTISING_CONTENT_BY_PROJECT_ID = "CACHE_KEY_FOR_ADVERTISING_CONTENT_BY_PROJECT_ID";
    /**
     * 缓存:根据id获取广告内容
     * @param id
     * @return
     */
    private AdvertisingEntityPojo loadAdvertisingByIDFromCache(Long id)
    {
        AdvertisingEntityPojo advertising = null;
        try
        {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String,String,Object> boundHashOps = redisTemplate.boundHashOps(CACHE_KEY_FOR_ADVERTISING_CONTENT_BY_PROJECT_ID);
            Object dataMap = boundHashOps.get(id.toString());
            if (dataMap != null) {
                advertising = new BeanUtil<AdvertisingEntityPojo>().convertOneMapListToOneBean((LinkedHashMap)dataMap,AdvertisingEntityPojo.class);
            }
            if (advertising == null)
            {
                advertising = this.loadAdvertisingByIDFromDB(id);
                boundHashOps.put(id.toString(),advertising);
                boundHashOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3, 0, 0, 0).getTime());
                LOGGER.debug("数据库:根据id获取广告内容");
            }
            else{
                LOGGER.debug("缓存:根据id获取广告内容");
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.loadAdvertisingByIDFromCache(Long id)",e);
            throw new RuntimeException("缓存:根据id获取广告内容出错");
        }
        return advertising;
    }

    /**
     * 数据库:根据id获取广告内容
     * @param id
     * @return
     */
    public AdvertisingEntityPojo loadAdvertisingByIDFromDB(Long id)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = Query.query(Criteria.where("id").is(id));

            AdvertisingEntityPojo advertisingEntityDB = mongoTemplate.findOne(query,AdvertisingEntityPojo.class,AdvertisingEntityPojo.ENTITY_NAME);
            return advertisingEntityDB;

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.getAdvertisingByID(Integer id)",e);
            throw new RuntimeException("根据id获取广告内容出错");
        }
    }


    private static final String CACHE_KEY_FOR_ADVERTISING_ID_LIST_BY_PROJECT_ID_WITH_TYPE_ID = "CACHE_KEY_FOR_ADVERTISING_ID_LIST_BY_PROJECT_ID_WITH_TYPE_ID";
    /**
     * 缓存:通过项目id查找今天所属的广告ID列表
     * @param condition
     * @return
     */
    private List<Long> fetchTodayAdvertisingIDListByProjectIDFromCache(AdvertisingEntityPojo condition)
    {
        List idList = null;
        try
        {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            String redisKey = CACHE_KEY_FOR_ADVERTISING_ID_LIST_BY_PROJECT_ID_WITH_TYPE_ID + condition.getType().toString();
            BoundHashOperations<String,String,List<Long>> boundHashOps = redisTemplate.boundHashOps(redisKey);
            idList = boundHashOps.get(condition.getProjectID().toString());
            if (idList == null)
            {
                idList = this.fetchTodayAdvertisingIDListByProjectIDFromDB(condition);
                boundHashOps.put(condition.getProjectID().toString(),idList);
                boundHashOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
                LOGGER.debug("数据库:根据项目id获取广告列表id");
            }
            else{
                LOGGER.debug("缓存:根据项目id获取广告列表id");
            }

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.fetchTodayAdvertisingListByProjectIDFromCache(Long projectID)",e);
            throw new RuntimeException("缓存:通过项目id查找今天所属的广告ID列表出错");
        }

        List<Long> dataIDList = new ArrayList<>();
        Long idVar = null;
        for (Object idObjVar : idList) {
            if (idObjVar instanceof Integer) idVar = ((Integer) idObjVar).longValue();
            else idVar = (Long)idObjVar;
            dataIDList.add(idVar);
        }
        return dataIDList;
    }


    /**
     * 数据库:通过项目id查找今天所属的广告ID列表
     * @param condition
     * @return
     */
    private List<Long> fetchTodayAdvertisingIDListByProjectIDFromDB(AdvertisingEntityPojo condition)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
//            long todayMillis = Calendar.getInstance().getTimeInMillis();
            Long tomorrowZero = DateUtils.truncateDate(Calendar.getInstance()).getTimeInMillis();

            Long projectID = condition.getProjectID();
            if (projectID == null) throw new RuntimeException("请传递所属项目");

            Integer type = condition.getType();
            if (type == null) type = AdvertisingTypeEnum.START_UP.getId();

            Criteria where = Criteria.where("projects").in(projectID).and("type").is(type).and("effectiveStartTime").lte(tomorrowZero).and("effectiveEndTime").gte(tomorrowZero);

            query.addCriteria(where);
            query.fields().include("id");
            List<AdvertisingEntityDB> list = mongoTemplate.find(query,AdvertisingEntityDB.class,AdvertisingEntityDB.ENTITY_NAME);
            List<Long> idList = new ArrayList<>();
            list.forEach(e -> idList.add(e.getId()));
            return idList;

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.AdvertisingServiceImpl.fetchTodayAdvertisingListByProjectID(Integer projectID)",e);
            throw new RuntimeException("数据库:通过项目id查找今天所属的广告出错");
        }
    }

    /**
     * 通过项目id查找今天所属的广告
     * @param condition
     * @return
     */
    public List<AdvertisingEntityPojo> fetchTodayAdvertisingListByProjectID(AdvertisingEntityPojo condition)
    {
        List<AdvertisingEntityPojo> data = new ArrayList<>();
        List<Long> idList = this.fetchTodayAdvertisingIDListByProjectIDFromCache(condition);
        if (idList != null) {
            for (Long id : idList) {
                AdvertisingEntityPojo adVar  = this.loadAdvertisingByIDFromCache(id);
                data.add(adVar);
            }
        }
        return data;
    }


}
