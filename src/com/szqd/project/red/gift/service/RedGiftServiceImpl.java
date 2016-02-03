package com.szqd.project.red.gift.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.model.SelectEntity;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.BeanUtil;
import com.szqd.framework.util.DateUtils;
import com.szqd.framework.util.URLConnectionUtils;
import com.szqd.framework.util.URLConnectionUtilsParam;
import com.szqd.project.red.gift.model.GiftEntityDB;
import com.szqd.project.red.gift.model.GiftEntityPOJO;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by like on 9/9/15.
 */
public class RedGiftServiceImpl extends SuperService implements RedGiftService {
    private static final Logger LOGGER = Logger.getLogger(RedGiftServiceImpl.class);

    /**
     * 随机红包
     * @return
     */
    public GiftEntityPOJO fetchRandomRedGift()
    {
        List<Long> idList = this.loadRedGiftTotalCountFromCache();
        int redGiftSize = idList.size();
        int randomIndex = new Random().nextInt(redGiftSize);
        LOGGER.debug("随机数为:" + randomIndex);
        for (int i = 0; i < idList.size(); i++) {
            Object giftIDVar =  idList.get(i);
            Long giftID = null;
            if (giftIDVar instanceof Integer) giftID = ((Integer) giftIDVar).longValue();
            else giftID = (Long)giftIDVar;
            if (randomIndex == i){
                GiftEntityPOJO randomGift = this.loadGiftByIDFromCache(giftID);
                return randomGift;
            }
        }
        return null;
    }

    private static final String REDIS_CACHE_KEY_FOR_TOTAL_GIFT_ID_LIST = "REDIS_CACHE_KEY_FOR_TOTAL_GIFT_ID_LIST";

    /**
     * 通过缓存读取所有红包的id
     * @return
     */
    public List<Long> loadRedGiftTotalCountFromCache()
    {
        List<Long> idList = null;
        try {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundValueOperations<String,List<Long>> boundOps = redisTemplate.boundValueOps(REDIS_CACHE_KEY_FOR_TOTAL_GIFT_ID_LIST);
            idList = boundOps.get();
            if (idList == null)
            {
                idList = this.loadRedGiftTotalCountFromDB();
                boundOps.set(idList);
                boundOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
                LOGGER.debug("数据库:读取红包的所有id");
            }
            else{
                LOGGER.debug("缓存:读取红包的所有id");
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.loadRedGiftTotalCountFromCache()",e);
            throw new RuntimeException("通过缓存读取所有红包的id出错");
        }
        return idList;
    }

    /**
     * 通过数据库读取所有红包的id
     * @return
     */
    public List<Long> loadRedGiftTotalCountFromDB()
    {
        GiftEntityPOJO condition = new GiftEntityPOJO();
        condition.setIsValidateExpire(true);
        condition.setFormType(1);
        List<GiftEntityPOJO> resultList = this.listGiftWithCondition(condition, null, "id");
        List<Long> idList = new ArrayList<>();
        resultList.forEach(e -> idList.add(e.getId()));
        return idList;
    }

    /**
     * 获取某一页的红包内容列表
     * @param pager
     * @return
     */
    public List<GiftEntityPOJO> fetchRedGiftContentListByPage(GiftEntityPOJO condition,Pager pager) {
        List<GiftEntityPOJO> list = new ArrayList<>();

        List<Long> giftIDList = null;
        Integer formType = condition.getFormType();
        if (formType.equals(1))
        {
            Integer type = condition.getType();
            if (type == null)
            {
                giftIDList = this.fetchGiftIDWithPageFromCache(condition,pager);
            }
            else{
                giftIDList = this.fetchGiftIDForTypeWithPageFromCache(condition,pager);
            }

        }
        else if (formType.equals(2)){
            giftIDList = this.fetchBannerIDWithPageFromCache(condition,pager);
        }

        for (Object giftID : giftIDList) {
            Long id = null;
            if (giftID instanceof Integer) id = ((Integer) giftID).longValue();
            else id = (Long)giftID;
            GiftEntityPOJO giftVar = this.loadGiftByIDFromCache(id);
            list.add(giftVar);
        }
        return list;
    }



    /**
     * 保存或更新红包
     * @param gift
     */
    public void saveOrUpdateGift(GiftEntityPOJO gift) {
        try {
            if (gift != null) {
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                Query query = new Query();
                if (gift.getId() == null)
                    gift.setId(this.getNextSequenceForFieldKey(GiftEntityDB.ENTITY_NAME));
                else
                {
                    this.removeCacheForRedGiftContentByID(gift.getId());
                }


                query.addCriteria(Criteria.where("_id").is(gift.getId()));
                Update update = new Update();
                String icon = gift.getIcon();
                if (icon != null) {
                    update.set("icon", icon);
                }

                String giftName = gift.getGiftName();
                if (giftName != null) {
                    update.set("giftName", gift.getGiftName());
                }

                String giftInfo = gift.getGiftInfo();
                if (giftInfo != null) {
                    update.set("giftInfo", giftInfo);
                }

                String giftDesc = gift.getGiftDesc();
                if (giftDesc != null) {
                    update.set("giftDesc", giftDesc);
                }

                String giftComment = gift.getGiftComment();
                if (giftComment != null) {
                    update.set("giftComment",giftComment);
                }

                String url = gift.getUrl();
                if (url != null) {
                    update.set("url", url);
                }

                Long beginTime = gift.getBeginTime();
                if (beginTime != null) {
                    update.set("beginTime", beginTime);
                }

                Long endTime = gift.getEndTime();
                if (endTime != null) {
                    update.set("endTime", endTime);
                }

                Integer type = gift.getType();
                if (type != null)
                {
                    update.set("type",type);
                }

                Integer formType = gift.getFormType();
                if (formType != null)
                {
                    update.set("formType",formType);
                }

                Integer orderNo = gift.getOrderNo();
//                if (orderNo != null)
//                {
                update.set("orderNo",orderNo);
//                }

                Boolean isValidateExpire = gift.getIsValidateExpire();
                if (isValidateExpire != null)
                {
                    Calendar now = Calendar.getInstance();
                    now.add(Calendar.DAY_OF_MONTH,-10);
                    update.set("beginTime", now.getTimeInMillis());
                    update.set("endTime", now.getTimeInMillis());
                }

                update.setOnInsert("createTime", Calendar.getInstance().getTimeInMillis());
                mongoTemplate.upsert(query, update, GiftEntityDB.ENTITY_NAME);

                RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
                redisTemplate.delete(REDIS_CACHE_KEY_FOR_GIFT_PAGE);
                redisTemplate.delete(REDIS_CACHE_KEY_FOR_TOTAL_GIFT_ID_LIST);
                redisTemplate.delete(REDIS_CACHE_KEY_FOR_BANNER_PAGE);

                String keyVar = REDIS_CACHE_KEY_GIFT_PAGE_FOR_TYPE_OF_ + gift.getType();
                redisTemplate.delete(keyVar);

//                gift = new GiftEntityPOJO();
//                List<SelectEntity> typeList = gift.getTypeList();
//                for (SelectEntity typeVar : typeList) {
//                }

//                this.loadGiftByIDFromCache(gift.getId());

            }
        } catch (Throwable e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.saveOrUpdateGift(GiftEntityDB gift)", e);
            throw new RuntimeException("保存或更新红包出错");
        }

    }


    /**
     * 通过id移除缓存中的红包内容
     *
     * @param id
     */
    private void removeCacheForRedGiftContentByID(Long id) {
        try {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String, Long, GiftEntityPOJO> boundHashOps = redisTemplate.boundHashOps(REDIS_CACHE_KEY_FOR_GIFT_CONTENT);
            boundHashOps.delete(id.toString());
        } catch (Throwable e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.removeCacheForRedGiftContentByID(Long id)", e);
            throw new RuntimeException("通过id移除缓存中的红包内容出错");
        }
    }

    /**
     * 通过条件获取到红包的分页数据
     *
     * @param condition
     * @param pager
     * @return
     */
    public List<GiftEntityPOJO> listGiftForManagement(GiftEntityPOJO condition, Pager pager) {
        return this.listGiftWithCondition(condition, pager);
    }

    private static final String REDIS_CACHE_KEY_FOR_GIFT_CONTENT = "REDIS_CACHE_KEY_FOR_GIFT_CONTENT";

    /**
     * 通过ID查询缓存中的红包
     *
     * @param id
     * @return
     */
    public GiftEntityPOJO loadGiftByIDFromCache(long id) {
        GiftEntityPOJO gift = null;
        try {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            BoundHashOperations<String,String,GiftEntityPOJO> ops = redisTemplate.boundHashOps(REDIS_CACHE_KEY_FOR_GIFT_CONTENT);
            String pageKey = String.valueOf(id);
            Object cacheObj = ops.get(pageKey);
            if (cacheObj != null )
            {
                gift = new BeanUtil<GiftEntityPOJO>().convertOneMapListToOneBean((LinkedHashMap)cacheObj, GiftEntityPOJO.class);
                LOGGER.debug("缓存:加载红包内容");
            }
            else
            {
                gift = this.loadGiftByIDFromDB(id);
                ops.put(pageKey, gift);
                Long expire = ops.getExpire();
                LOGGER.debug("红包内容剩余过期时间为:" + expire);

                if(expire.equals(-1l))
                {
                    ops.expireAt(DateUtils.getTomorrowForSpecifiedTime(3, 0,0,0).getTime());
                    LOGGER.debug("红包内容设置了过期的时间");
                }

                LOGGER.debug("数据库:加载红包内容");
            }

        } catch (Throwable e) {
            LOGGER.error("通过ID查询缓存中的红包", e);
            throw new RuntimeException("通过ID查询缓存中的红包出错");
        }
        return gift;
    }

    /**
     * 通过ID查询数据库中的红包
     *
     * @param id
     * @return
     */
    public GiftEntityPOJO loadGiftByIDFromDB(Long id) {
        GiftEntityPOJO gift = null;
        try {
            if (id != null) {
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(id));
                gift = mongoTemplate.findOne(query, GiftEntityPOJO.class, GiftEntityPOJO.ENTITY_NAME);
            }
            return gift;
        } catch (Throwable e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.findGiftByID(condition:GiftEntityDB)", e);
            throw new RuntimeException("通过查询红包出错");
        }
    }

    private static final String REDIS_CACHE_KEY_FOR_GIFT_PAGE = "REDIS_CACHE_KEY_FOR_GIFT_PAGE";

    private static final String REDIS_CACHE_KEY_FOR_BANNER_PAGE = "REDIS_CACHE_KEY_FOR_BANNER_PAGE";



    private List<Long> fetchBannerIDWithPageFromCache(GiftEntityPOJO condition,Pager pager) {
        try {
            if (pager.getPageNo() < 1) {
//                throw new RuntimeException("页码小于1");
                pager.setPageNo(1);
            }
            condition.setFormType(2);

            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String, String, List<Long>> boundHashOps = redisTemplate.boundHashOps(REDIS_CACHE_KEY_FOR_BANNER_PAGE);
            String pageKey = String.valueOf(pager.getPageNo());
            List<Long> pageIdList = boundHashOps.get(pageKey);
            if (pageIdList == null)
            {
                pageIdList = fetchGiftIDWithPageFromDB(condition,pager);
                boundHashOps.put(pageKey,pageIdList);
                Long expire = boundHashOps.getExpire();

                LOGGER.debug("BANNER页码剩余的过期时间为:"+expire);
                if (expire.equals(-1l)) {
                    boundHashOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
                    LOGGER.debug("设置BANNER页码过期时间");
                }
                LOGGER.debug("数据库:读取第" + pager.getPageNo() + "页的BANNER列表");
            } else {
                LOGGER.debug("缓存:读取第" + pager.getPageNo() + "页的BANNER列表");
            }

            return pageIdList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.fetchGiftIDWithPageFromCache",e);
            throw new RuntimeException("从缓存里面获取BANNER页的id列表出错");
        }
    }

    /**
     * 从缓存里面获取页码
     *
     * @param pager
     * @return
     */
    private List<Long> fetchGiftIDWithPageFromCache(GiftEntityPOJO condition,Pager pager) {
        try {
            if (pager.getPageNo() < 1) {
                throw new RuntimeException("页码小于1");
            }
            condition.setFormType(1);

            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String, String, List<Long>> boundHashOps = redisTemplate.boundHashOps(REDIS_CACHE_KEY_FOR_GIFT_PAGE);
            String pageKey = String.valueOf(pager.getPageNo());
            List<Long> pageIdList = boundHashOps.get(pageKey);
            if (pageIdList == null)
            {
                pageIdList = fetchGiftIDWithPageFromDB(condition,pager);
                boundHashOps.put(pageKey,pageIdList);
                Long expire = boundHashOps.getExpire();

                LOGGER.debug("红包页码剩余的过期时间为:"+expire);
                if (expire.equals(-1l)) {
                    boundHashOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
                    LOGGER.debug("设置红包页码过期时间");
                }
                LOGGER.debug("数据库:读取第" + pager.getPageNo() + "页的礼物列表");
            } else {
                LOGGER.debug("缓存:读取第" + pager.getPageNo() + "页的礼物列表");
            }

            return pageIdList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.fetchGiftIDWithPageFromCache",e);
            throw new RuntimeException("从缓存里面获取礼物页的id列表出错");
        }
    }


    private static final String REDIS_CACHE_KEY_GIFT_PAGE_FOR_TYPE_OF_ = "REDIS_CACHE_KEY_GIFT_PAGE_FOR_TYPE_OF_";
    /**
     * 从缓存里面获取页码
     *
     * @param pager
     * @return
     */
    private List<Long> fetchGiftIDForTypeWithPageFromCache(GiftEntityPOJO condition,Pager pager) {
        try {
            if (pager.getPageNo() < 1) {
                throw new RuntimeException("页码小于1");
            }
            condition.setFormType(1);
            Integer type = condition.getType();
            if (type == null) type = 1;


            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            String hashKey = REDIS_CACHE_KEY_GIFT_PAGE_FOR_TYPE_OF_ + type;

            BoundHashOperations<String, String, List<Long>> boundHashOps = redisTemplate.boundHashOps(hashKey);
            String pageKey = String.valueOf(pager.getPageNo());
            List<Long> pageIdList = boundHashOps.get(pageKey);
            if (pageIdList == null)
            {
                pageIdList = fetchGiftIDWithPageFromDB(condition,pager);
                boundHashOps.put(pageKey,pageIdList);
                Long expire = boundHashOps.getExpire();

                LOGGER.debug("红包页码剩余的过期时间为:"+expire);
                if (expire.equals(-1l)) {
                    boundHashOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
                    LOGGER.debug("设置红包页码过期时间");
                }
                LOGGER.debug("数据库:读取第" + pager.getPageNo() + "页的礼物列表");
            } else {
                LOGGER.debug("缓存:读取第" + pager.getPageNo() + "页的礼物列表");
            }

            return pageIdList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.fetchGiftIDWithPageFromCache",e);
            throw new RuntimeException("从缓存里面获取礼物页的id列表出错");
        }
    }


    /**
     * 通过数据库获取到红包ID的分页数据
     *
     * @param pager
     * @return
     */
    private List<Long> fetchGiftIDWithPageFromDB(GiftEntityPOJO condition,Pager pager) {
        condition.setIsValidateExpire(true);
        List<GiftEntityPOJO> pageList = this.listGiftWithCondition(condition, pager,"id");
        List<Long> idList = new ArrayList<>();
        pageList.forEach(e -> idList.add(e.getId()));
        return idList;
    }

    private static final String QUERY_STRING_GIFT_DYNAMIC = "giftName:{$regex:%s}}";

    /**
     * 通过条件获取到红包的分页数据
     * @param condition
     * @param pager
     * @return
     */
    private List<GiftEntityPOJO> listGiftWithCondition(GiftEntityPOJO condition, Pager pager,String...fields) {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Criteria criteria = new Criteria().where("");

            if (condition != null)
            {
                String giftNameRegex = condition.getGiftNameRegex();
                if (giftNameRegex != null) {
                    criteria = criteria.and("giftName").regex(giftNameRegex);
                }

                String giftInfoRegex = condition.getGiftInfoRegex();
                if (giftInfoRegex != null)
                {
                    criteria = criteria.and("giftInfo").regex(giftInfoRegex);
                }

                long nowTime = Calendar.getInstance().getTimeInMillis();
                Boolean isValidateExpire = condition.getIsValidateExpire();
                if (isValidateExpire == null || isValidateExpire.equals(true)){
                    criteria = criteria.and("beginTime").lte(nowTime).and("endTime").gte(nowTime);
                } else{
                    criteria = criteria.and("endTime").lt(nowTime);
                }

                Integer type = condition.getType();
                if (type != null){
                    criteria = criteria.and("type").is(type);
                }

                Integer formType = condition.getFormType();
                if (formType != null)
                {
                    criteria = criteria.and("formType").is(formType);
                }
            }
            Query query = new Query(criteria);

            Integer sortType = condition.getSortType();
            if (sortType != null && sortType.equals(2))
            {
                query.with(new Sort(Sort.Direction.DESC,"orderNo"));
            }
            else {
                query.with(new Sort(new Sort.Order(Sort.Direction.DESC,"orderNo"),new Sort.Order(Sort.Direction.DESC,"createTime")));
            }

            if (pager != null) {
                query.skip(pager.getOffset());
                query.limit(pager.getCapacity());
            }




            if (fields != null)
            {
                for (String fieldVar : fields) {
                    query.fields().include(fieldVar);
                }
            }

            List<GiftEntityPOJO> giftList = mongoTemplate.find(query, GiftEntityPOJO.class, GiftEntityPOJO.ENTITY_NAME);

            return giftList;
        } catch (Throwable e) {
            LOGGER.error("com.szqd.project.red.gift.service.RedGiftServiceImpl.listGiftWithCondtion(condition: GiftEntityDB, pager: Pager[AnyRef])", e);
            throw new RuntimeException("通过条件获取到红包的分页数据出错");
        }
    }


    private static final String URL_HOT_NEWS = "http://2345api.dfshurufa.com/top/shenzhishoudiantong";

    /**
     * 获取热门新闻
     * @return
     * @throws Exception
     */
    public Object getHotNews() throws Exception
    {
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(HOT_NEWS_TASK_KEY);

        return boundValueOps.get();
    }

    private static final String HOT_NEWS_TASK_KEY = "HOT_NEWS_TASK_KEY";
    public void fetchHotNewsForTask() throws Exception {
        URLConnectionUtilsParam param = new URLConnectionUtilsParam();
        param.urlAddr = URL_HOT_NEWS;
        param.method = "GET";
        param.encoding = "UTF-8";
        String newsJson = URLConnectionUtils.send(param);
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(HOT_NEWS_TASK_KEY);
        boundValueOps.set(newsJson);
    }

    private static final String URL_SPORTS_NEWS = "http://2345api.dfshurufa.com/tiyu/shenzhishoudiantong";

    /**
     * 获取体育新闻
     * @return
     * @throws Exception
     */
    public Object getSportsNews() throws Exception
    {
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(SPORTS_NEWS_TASK_KEY);
        return boundValueOps.get();
    }

    private static final String SPORTS_NEWS_TASK_KEY = "SPORTS_NEWS_TASK_KEY";
    public void fetchSportsNewsForTask() throws Exception {
        URLConnectionUtilsParam param = new URLConnectionUtilsParam();
        param.urlAddr = URL_SPORTS_NEWS;
        param.method = "GET";
        param.encoding = "UTF-8";
        String newsJson = URLConnectionUtils.send(param);
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(SPORTS_NEWS_TASK_KEY);
        boundValueOps.set(newsJson);
    }

    private static final String URL_MORE_NEWS = "http://toutiao.eastday.com/?qid=shenzhishoudiantong";

    /**
     * 获取更多新闻
     * @return
     */
    public String getMoreNewsURL()
    {
        return URL_MORE_NEWS;
    }


}
