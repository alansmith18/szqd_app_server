package com.szqd.project.common.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.szqd.framework.model.Pager;
import com.szqd.framework.service.ForwardURLService;
import com.szqd.framework.service.SuperService;
import com.szqd.framework.util.BeanUtil;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.common.model.push.*;
import com.szqd.project.mobile.lock.model.json.JSONForAppAnalysisEntity;
import com.szqd.project.mobile.lock.model.json.RecordListEntity;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * Created by like on 7/8/15.
 */
public class PushMessageServiceImpl extends SuperService implements PushMessageService,ForwardURLService
{
    private static final Logger LOGGER = Logger.getLogger(PushMessageServiceImpl.class);

    /**
     * 获取多个推送消息
     * @param userID
     * @param projectID
     * @param interestJson
     * @return
     */
    public List<PushMessageDB> fetchManyPushMessage(String userID,Integer projectID,String interestJson)
    {
        List<PushMessageDB> pushMessageList = new ArrayList<>();
        Set<Long> idList = new HashSet<>();
        for (int i = 0; i < 4 ; i++)
        {
            PushMessageDB pushMessageDB = this.fetchPushMessage(userID, projectID, interestJson);
            boolean isNotExists = !idList.contains(pushMessageDB.getId());
            if (isNotExists) pushMessageList.add(pushMessageDB);
        }
        return pushMessageList;
    }

    /**
     * 获取推送消息
     * @param userID
     * @param projectID
     * @param interestJson
     * @return
     */
    public PushMessageDB fetchPushMessage(String userID,Integer projectID,String interestJson)
    {
        LOGGER.debug("*******推送请求的参数:userID: "+userID+"  projectID: "+projectID+" interestJson: "+interestJson);
        Long pushID = this.getUserPushMessageFromUserStackByUserID(userID,projectID);
        PushMessageDB pushMessageDB = this.getPushMessageFromRedisByID(pushID);

        if (pushMessageDB == null)
        {
            List<Integer> categoryList = this.getInterestFromJson(interestJson);
            categoryList.add(MessageTypeEnum.MANUALLY.getId());
            LOGGER.debug("*******查询的兴趣为: "+categoryList);
            List<Long> pushMessageDBList = this.queryPushMessageIDListByAppCategoryIDList(categoryList,projectID);
            boolean isNotEmpty = !(pushMessageDBList == null || pushMessageDBList.isEmpty());
            if (isNotEmpty)
            {
                if (!pushMessageDBList.isEmpty()) {
                    this.generateUserPushMessageStack(userID,projectID, pushMessageDBList);
                }
            }

            pushID = this.getUserPushMessageFromUserStackByUserID(userID,projectID);
            pushMessageDB = this.getPushMessageFromRedisByID(pushID);
        }

        if (pushMessageDB != null)
        {
            /**
             * 暂时关闭记录推送数量,等待修改
             */
//            this.pushMessageService.upsetPushCountForToday(pushMessageDB.getId());
            pushMessageDB = this.changePushURLToMyServerURL(pushMessageDB);
            LOGGER.debug("&&&&&&&&&&&&客户端获取的推送消息为title: " + pushMessageDB.getTitle() + " subTitle: " + pushMessageDB.getSubTitle());
        }
        else {
            LOGGER.debug("&&&&&&&&&&&&没有推送消息");
        }

        return pushMessageDB;
    }

    /**
     * 根据分页罗列推送消息列表
     * @param queryCondition
     * @param pager
     * @return
     */
    public List<PushMessagePojo> listPushMessage(PushMessagePojo queryCondition,Pager pager)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Calendar now = Calendar.getInstance();
            now.set(Calendar.HOUR_OF_DAY,0);
            now.set(Calendar.MINUTE,0);
            now.set(Calendar.SECOND,0);
            now.set(Calendar.MILLISECOND, 0);
            now.add(Calendar.DAY_OF_MONTH, 1);
//            long nowTimeInMillis = now.getTimeInMillis();
            Criteria criteria = Criteria.where("");//Criteria.where("beginTime").lte(nowTimeInMillis).and("endTime").gte(nowTimeInMillis);
            if (queryCondition != null)
            {
                Integer pushType = queryCondition.getPushType();
                if (pushType != null)
                {
                    criteria = criteria.and("pushType").is(pushType);
                }

                List<Integer> messageTypes = queryCondition.getMessageTypes();
                if (messageTypes != null)
                {
                    criteria = criteria.and("messageTypes").in(messageTypes);
                }

                String subTitle = queryCondition.getSubTitle();
                if (subTitle != null)
                {
                    criteria = criteria.and("subTitle").regex(subTitle);
                }

                String title = queryCondition.getTitle();
                if (title != null)
                {
                    criteria = criteria.and("title").regex(title);
                }

                Integer urlType = queryCondition.getUrlType();
                if (urlType != null)
                {
                    criteria = criteria.and("urlType").is(urlType);
                }

                Integer project = queryCondition.getProject();
                if (project != null)
                {
                    criteria = criteria.and("project").is(project);
                }

                Long beginTime = queryCondition.getBeginTime();
                Criteria beginDateCriteria = new Criteria();
                if(beginTime != null)
                {
                    beginDateCriteria = beginDateCriteria.and("beginTime").gte(beginTime);
                }

                Long endTime = queryCondition.getEndTime();
                Criteria endDateCriteria = new Criteria();
                if (endTime != null)
                {
                    endDateCriteria = endDateCriteria.and("endTime").lte(endTime);
                }

                Criteria beginDateForUnexpiredCriteria = new Criteria();
                Criteria endDateForUnexpiredCriteria = new Criteria();
                Boolean unexpired = queryCondition.getUnexpired();
                if (unexpired != null && unexpired)
                {
                    Long tomorrowZero = DateUtils.truncateDate(Calendar.getInstance()).getTimeInMillis();
                    beginDateForUnexpiredCriteria = beginDateForUnexpiredCriteria.and("beginTime").lte(tomorrowZero);
                    endDateForUnexpiredCriteria = endDateForUnexpiredCriteria.and("endTime").gte(tomorrowZero);
                }

                criteria.andOperator(beginDateCriteria, endDateCriteria,beginDateForUnexpiredCriteria,endDateForUnexpiredCriteria);
            }
            query.addCriteria(criteria);
            query.skip(pager.getOffset());
            query.limit(pager.getCapacity());
            Sort sort = new Sort(Sort.Direction.ASC,"orderBy");
            query.with(sort);
            List<PushMessagePojo> pushMessageList = mongoTemplate.find(query,PushMessagePojo.class, PushMessageDB.ENTITY_NAME);
            return pushMessageList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.listPushMessage(PushMessage queryCondition,Pager pager)",e);
            throw new RuntimeException("根据分页罗列推送消息列表出错");
        }
    }

    /**
     * 通过id获取推送消息
     * @param id
     * @return
     */
    public PushMessagePojo fetchPushMessageByID(Integer id)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query(Criteria.where("id").is(id));
            PushMessagePojo pushMessage = mongoTemplate.findOne(query,PushMessagePojo.class, PushMessageDB.ENTITY_NAME);
            return pushMessage;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.fetchPushMessageByID(Integer id)",e);
            throw new RuntimeException("通过id获取推送消息出错");
        }

    }

    /**
     * 添加推送消息
     * @param pushMessage
     */
    public void savePushMessage(PushMessageDB pushMessage)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Long id = pushMessage.getId();
            if (id == null)
            {
                id = this.getNextSequenceForFieldKey(PushMessageDB.ENTITY_NAME);
            }
            else {
                String redisKey = this.getPushMessageKey(id);
                RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
                redisTemplate.delete(redisKey);
            }

            Query query = new Query(Criteria.where("_id").is(id));
            Update update = new Update();
            update.setOnInsert("_id", id);
            update.set("icon", pushMessage.getIcon());
            update.set("contentPic",pushMessage.getContentPic());
            update.set("appName",pushMessage.getAppName());
            update.set("title",pushMessage.getTitle());
            update.set("subTitle", pushMessage.getSubTitle());
            update.set("project",pushMessage.getProject());
            update.set("targetURL", pushMessage.getTargetURL());
            update.set("urlType",pushMessage.getUrlType());
            update.set("packageName", pushMessage.getPackageName());
            update.set("messageTypes", pushMessage.getMessageTypes());
            update.set("pushType", pushMessage.getPushType());
            update.setOnInsert("createTime", Calendar.getInstance().getTimeInMillis());
            update.setOnInsert("orderBy", 0);
            update.set("beginTime", pushMessage.getBeginTime());
            update.set("endTime", pushMessage.getEndTime());

            mongoTemplate.upsert(query,update,PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.savePushMessage(PushMessage pushMessage)",e);
            throw new RuntimeException("添加推送消息出错");
        }
    }

    /**
     * 减少所有的推送排序序号
     */
    public void reducePushOrder()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Update update = new Update();
            update.inc("orderBy", 1);
            mongoTemplate.updateMulti(query, update, PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.reducePushOrder()",e);
            throw new RuntimeException("减少所有的推送排序序号出错");
        }
    }

    /**
     * 减少指定id号得推送的前面得推送的全部排序序号
     */
    public void reducePushOrderBeforeByID(Integer id)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{_id:%d}";
            queryString = String.format(queryString,id);
            String fields = "{_id:0,orderBy:1}";
            BasicQuery basicQuery = new BasicQuery(queryString,fields);
            PushMessageDB pushMessageDB = mongoTemplate.findOne(basicQuery, PushMessageDB.class, PushMessageDB.ENTITY_NAME);

            Query query = new Query(Criteria.where("orderBy").lte(pushMessageDB.getOrderBy()));
            Update update = new Update();
            update.inc("orderBy", 1);
            mongoTemplate.updateMulti(query, update, PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.reducePushOrder()",e);
            throw new RuntimeException("减少所有的推送排序序号出错");
        }
    }

    /**
     * 置顶推送消息
     * @param id
     */
    public void topPushMessageByID(Integer id)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query(Criteria.where("_id").is(id));
            Update update = new Update();
            update.set("orderBy", 0);
            mongoTemplate.updateFirst(query, update, PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.topPushMessageByID(Integer id)",e);
            throw new RuntimeException("置顶推送消息出错");
        }

    }

    private static final String CACHE_KEY_FOR_GET_PUSH_MESSAGE_ID_FROM_CATEGORY_ID = "CACHE_KEY_FOR_GET_PUSH_MESSAGE_ID_FROM_CATEGORY_ID";

    /**
     * 通过兴趣查找兴趣和人工推送的推送消息索引
     * @param appCategoryIDList
     * @return
     */
    public List<Long> queryPushMessageIDListByAppCategoryIDList(List<Integer> appCategoryIDList, Integer projectID)
    {
        try
        {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String,String,List<Long>> boundHashOps = redisTemplate.boundHashOps(CACHE_KEY_FOR_GET_PUSH_MESSAGE_ID_FROM_CATEGORY_ID);
            Date tomorrowHour3 = DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime();
            boundHashOps.expireAt(tomorrowHour3);

            Set pushIDSet = new HashSet<>();
            for (Integer categoryID : appCategoryIDList)
            {
                StringBuilder cacheField = new StringBuilder();
                cacheField.append(projectID).append(":").append(categoryID);
                String cacheFieldKey = cacheField.toString();
                List<Long> pushIDList = boundHashOps.get(cacheFieldKey);
                if (pushIDList == null)
                {

                    pushIDList = this.queryPushMessageIDListByAppCategoryIDFromDB(categoryID,projectID);
                    boundHashOps.put(cacheFieldKey,pushIDList);
                    LOGGER.debug("数据库:获取兴趣的推送id");
                }
                else
                    LOGGER.debug("缓存:获取兴趣的推送id");
                pushIDSet.addAll(pushIDList);
            }
            List<Long> changeToList = new ArrayList<>();
            for (Object o : pushIDSet) {
                Long val = null;
                if (o instanceof Integer) val = ((Integer) o).longValue();
                else val = (Long)o;
                changeToList.add(val);
            }

            return changeToList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.queryPushMessageIDListByAppCategoryID(List<Integer> appCategoryIDList, Integer projectID)",e);
            throw new RuntimeException("通过兴趣查找兴趣和人工推送出错");
        }
    }

    /**
     * 从数据库中查询兴趣和人工的推送
     * @param categoryID
     * @param projectID
     * @return
     */
    private List<Long> queryPushMessageIDListByAppCategoryIDFromDB(Integer categoryID, Integer projectID)
    {
        List<Long> resultList = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{beginTime:{$lte:%d},endTime:{$gte:%d},project:%d,messageTypes:{'$in':[%d]}}";
            String fieldsString = "{_id:1}";

            Calendar now = Calendar.getInstance();
            long todayTime = DateUtils.truncateDate(now).getTimeInMillis();

            queryString = String.format(queryString,todayTime,todayTime,projectID,categoryID);
            BasicQuery query = new BasicQuery(queryString,fieldsString);
            List<PushMessageDB> list = mongoTemplate.find(query,PushMessageDB.class,PushMessageDB.ENTITY_NAME);
            resultList = new ArrayList<>();
            for (PushMessageDB push : list) {
                resultList.add(push.getId());
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.queryPushMessageIDListByAppCategoryIDFromDB(Integer categoryID, Integer projectID)", e);
            throw new RuntimeException("从数据库中查询兴趣和人工的推送");
        }
        return resultList;
    }



    private static final String USER_PUSH_MESSAGE_STACK_PREFFIX = "#USER_PUSH_MESSAGE_STACK_FOR_%s:%d#";

    /**
     * 根据用户id,获取用户推送消息的key
     * @param userID
     * @return
     */
    public String getUserPushMessageStackKey(String userID, Integer projectID)
    {
        String userKey = String.format(USER_PUSH_MESSAGE_STACK_PREFFIX, userID, projectID);
        return userKey;
    }

    private static final String SYSTEM_PUSH_MESSAGE_STACK_PREFFIX = "#SYSTEM_PUSH_MESSAGE_STACK#";

    /**
     * 获取系统推送消息的堆栈的key
     * @return
     */
    public String getSystemPushMessageStackKey()
    {
        return SYSTEM_PUSH_MESSAGE_STACK_PREFFIX;
    }

    /**
     * 获取到所有的推送消息
     * @return
     */
    private List<PushMessageDB> getAllPushMessage()
    {
        List<PushMessageDB> allPushMessage = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            allPushMessage = mongoTemplate.findAll(PushMessageDB.class,PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.getAllPushMessage()",e);
            throw new RuntimeException("获取到所有的推送消息出错");
        }
        return allPushMessage;
    }

    private static final String PUSH_MESSAGE_KEY_PREFFIX = "#PUSH_MESSAGE_KEY_%d#";
    private String getPushMessageKey(Long pushID)
    {
        return String.format(PUSH_MESSAGE_KEY_PREFFIX,pushID);
    }





//    /**
//     * 生成系统的推送堆栈
//     */
//    public void generateSystemPushMessageStack()
//    {
//        try
//        {
//            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
//            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//            List<PushMessageDB> allPushMessageList = this.getAllPushMessage();
//            if (allPushMessageList != null)
//            {
//                for (PushMessageDB push : allPushMessageList)
//                {
//                    Integer id = push.getId();
//                    String pushMessageKey = this.getPushMessageKey(id);
//                    BoundValueOperations boundValueOps = redisTemplate.boundValueOps(pushMessageKey);
//                    boundValueOps.set(push);
//                }
//            }
//
//
//        } catch (Exception e) {
//            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.generateSystemPushMessageStack()",e);
//            throw new RuntimeException("生成系统的推送堆栈出错");
//        }
//
//    }

    /**
     * 生成用户的推送堆栈
     * @param userID
     * @param userPushMessageList
     */
    public void generateUserPushMessageStack(String userID,Integer projectID,List<Long> userPushMessageList)
    {
        try
        {
            if (userPushMessageList != null && !userPushMessageList.isEmpty()) {
                RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
                String userKey = this.getUserPushMessageStackKey(userID, projectID);
                BoundListOperations boundListOperations = redisTemplate.boundListOps(userKey);
                boundListOperations.leftPushAll(userPushMessageList.toArray());

                Calendar now = Calendar.getInstance();
                Date tomorrow = DateUtils.roundDate(now).getTime();
                redisTemplate.expireAt(userKey, tomorrow);
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.generateUserPushMessageStack(String userID,List<PushMessageDB> userPushMessageList)",e);
            throw new RuntimeException("生成用户的推送堆栈出错");
        }
    }

    /**
     * 通过用户id获取推送消息id
     * @param userID
     * @return
     */
    public Long getUserPushMessageFromUserStackByUserID(String userID, Integer projectID)
    {
        Long pushID = null;
        try
        {
            String userKey = this.getUserPushMessageStackKey(userID, projectID);
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            boolean hasKey = redisTemplate.hasKey(userKey);
            if (!hasKey)
                return pushID;

            BoundListOperations boundListOps = redisTemplate.boundListOps(userKey);
            boolean isEmpty = 0 == boundListOps.size();
            if (isEmpty)
            {
                redisTemplate.delete(userKey);
            }
            else {
                Object pushIDObj = boundListOps.rightPop();
                if (pushIDObj instanceof Integer)
                {
                    Integer pushIDObjInteger = (Integer)pushIDObj;
                    pushID = Long.valueOf(pushIDObjInteger);
                }
                else
                    pushID = (Long)pushIDObj;

            }

            return pushID;

        }
        catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.getUserPushMessageFromUserStackByUserID(String userID)",e);
            throw new RuntimeException("通过用户id获取推送消息列表出错");
        }
    }



    /**
     * 从mongodb,通过推送消息id,获取推送消息
     * @param pushMessageID
     * @return
     */
    private PushMessageDB getPushMessageFromMongoByID(Long pushMessageID)
    {
        PushMessageDB pushMessageDB = null;
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query(Criteria.where("id").is(pushMessageID));
            pushMessageDB = mongoTemplate.findOne(query,PushMessageDB.class,PushMessageDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.getPushMessageFromMongoByID(Integer pushMessageID)",e);
            throw new RuntimeException("*****出错:从mongodb,通过推送消息id,获取推送消息");
        }
        return pushMessageDB;
    }

    @Override
    public Object executeService(Map<String, Object> param)
    {
        try {
            Integer pushID = Integer.valueOf((String) param.get("key"));
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(pushID));
            Update update = new Update();
            update.setOnInsert("_id",pushID);
            update.inc("clickCount",1);
            mongoTemplate.upsert(query,update,ClickRecordDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.executeService(Map<String, Object> param)");
        }
        return null;
    }


    public PushMessageDB changePushURLToMyServerURL(PushMessageDB pushMessageDB)
    {

        Long key = pushMessageDB.getId();
        String action = pushMessageDB.getTargetURL();

        action = action.replaceAll("/","*"); //StringUtil.simpleEncodeForString(action);

        String beanID = "pushMessageService";
        String changeURL = this.generateURIForForward(action,beanID,String.valueOf(key));
        pushMessageDB.setTargetURL(changeURL);
        return pushMessageDB;
    }

    /**
     * 从redis,通过推送id获取推送消息
     * @param pushMessageID
     * @return
     */
    public PushMessageDB getPushMessageFromRedisByID(Long pushMessageID)
    {
        if (pushMessageID == null)
            return null;

        PushMessageDB result = null;
        try {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            String pushMessageKey = this.getPushMessageKey(pushMessageID);

            BoundValueOperations boundValueOps = redisTemplate.boundValueOps(pushMessageKey);
            boundValueOps.expireAt(DateUtils.getTomorrowForSpecifiedTime(3,0,0,0).getTime());
            Object pushMessageObj = null;
            if (redisTemplate.hasKey(pushMessageKey))
            {
                pushMessageObj = boundValueOps.get();

            }

            if (pushMessageObj == null)
            {
                result = this.getPushMessageFromMongoByID(pushMessageID);
                if (result != null) {
                    boundValueOps.set(result);
                    LOGGER.debug("数据库:读取推送内容");
                }
            }
            else
            {
                result = (PushMessageDB)new BeanUtil<PushMessageDB>().convertMapToBean(pushMessageObj,PushMessageDB.class);
                LOGGER.debug("缓存:读取推送内容");
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.getPushMessageFromRedisByID(Integer pushMessageID)",e);
            throw new RuntimeException("####从redis,通过推送id获取推送消息");
        }

        return result;
    }



    /**
     * 解析兴趣json
     * @param interestJsonString
     * @return
     */
    private List<InterestJson> parseInterestJsonFromClientString(String interestJsonString)
    {
        List<InterestJson> interestList = null;
        try {
            Gson gson = new Gson();
            interestList = gson.fromJson(interestJsonString,
                    new TypeToken<ArrayList<InterestJson>>() {}.getType());
        } catch (JsonSyntaxException e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.parseInterestJsonFromClientString(String interestJsonString)",e);
            throw new RuntimeException("解析兴趣json出错");
        }
        return interestList;
    }

    /**
     * 通过兴趣json获取到各个类别的使用次数
     * @param interestJson
     * @return
     */
    private Map<Integer,InterestJson> getUseCountOfCategoryFromInterestJson(String interestJson)
    {
        Map<Integer,InterestJson> categoryUseCountMap = new HashMap<>();
        if (interestJson != null && !interestJson.trim().equals("")) {
            List<InterestJson> interestJsonList = this.parseInterestJsonFromClientString(interestJson);
            for (InterestJson json : interestJsonList) {
                String appName = json.getAppName();
                Integer category = this.queryCategoryByAppName(appName);
                InterestJson interestEntity = categoryUseCountMap.get(category);

                if (interestEntity == null)
                {
                    interestEntity = new InterestJson();
                    interestEntity.setUseTime(0);
                    interestEntity.setLaunchTimes(0);
                }
                interestEntity.setLaunchTimes(interestEntity.getLaunchTimes()+json.getLaunchTimes());
                interestEntity.setUseTime(interestEntity.getUseTime()+json.getUseTime());
                categoryUseCountMap.put(category,interestEntity);
            }
        }
        return categoryUseCountMap;
    }

    /**
     * 通过json获取兴趣的类别id
     * @param interestJson
     * @return
     */
    public List<Integer> getInterestFromJson(String interestJson)
    {
        List<Integer> interestCategoryList = null;
        try {
            interestCategoryList = new ArrayList<>();
            Map<Integer,InterestJson> categoryUseCountMap = this.getUseCountOfCategoryFromInterestJson(interestJson);
            for (Integer categoryID : categoryUseCountMap.keySet())
            {

                if (categoryID.equals(new Integer(-1))) {
                    continue;
                }
                InterestJson interestEntity = categoryUseCountMap.get(categoryID);

                MessageTypeEnum categoryEnum = MessageTypeEnum.getNameByID(categoryID);
                Integer interestCount = categoryEnum.getInterestCount();
                Long interestTime = categoryEnum.getInterestTime();
                boolean isInterestCategory = interestEntity.getLaunchTimes() >= interestCount || interestEntity.getUseTime() >= interestTime;
                if (isInterestCategory)
                    interestCategoryList.add(categoryID);
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.getInterestFromJson(String interestJson)",e);
            throw new RuntimeException("通过json获取兴趣的类别id出错");
        }
        return interestCategoryList;
    }

    /**
     * 更新今天的推送数量
     * @param pushID
     */
    public void upsetPushCountForToday(Long pushID)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Calendar now = Calendar.getInstance();
            now = DateUtils.truncateDate(now);
            Criteria criteria = Criteria.where("pushID").in(pushID).and("createTime").is(now.getTimeInMillis());
            Query query = new Query(criteria);

            Update update = new Update();
            update.set("pushID", pushID);
            update.set("createTime",now.getTimeInMillis());
            update.inc("pushCount", 1);
            mongoTemplate.upsert(query, update, PushRecord.class, PushRecord.ENTITY_NAME);

        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.upsetPushCountForToday(List<Integer> categories)",e);
            throw new RuntimeException("更新今天的推送数量出错");
        }

    }

    public void testUserData(String userID,String descType)
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
        Calendar today = DateUtils.truncateDate(Calendar.getInstance());
        Query query = new Query(Criteria.where("userID").is(userID).and("descType").is(descType).and("createTime").is(today.getTimeInMillis()));
        Update update = new Update();
        update.set("userID",userID);
        update.set("descType",descType);
        update.inc("count", 1);
        update.set("createTime", today.getTimeInMillis());

        mongoTemplate.upsert(query,update,"TEST_PUSH_MESSAGE_COUNT_FOR_USER");
    }

    /**
     * 预测明天的推送数量
     * @return
     */
    public Long forecastPushCountForTomorrow()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Long days = mongoTemplate.count(new Query(), PushRecord.ENTITY_NAME);

            GroupOperation groupOperation = Aggregation.group().sum("pushCount").as("totalPushCount");
            ProjectionOperation projectionOperation = Aggregation.project("totalPushCount");
            List<AggregationOperation> aggregationList = Arrays.asList(groupOperation, projectionOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationList);
            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation, PushRecord.ENTITY_NAME, HashMap.class);
            HashMap<String,Integer> map = aggregationResults.getUniqueMappedResult();
            long totalPushCount = map.get("totalPushCount");
            return totalPushCount/days;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.forecastPushCountForTomorrow()",e);
            throw new RuntimeException("预测明天的推送数量出错");
        }


    }



    /**
     * 接受用户安装的app信息
     * @param json
     */
    public void receiveInstallApp(String json)
    {
        try
        {
            Gson gson = new Gson();
            JSONForAppAnalysisEntity jsonForAppAnalysisEntity = gson.fromJson(json, JSONForAppAnalysisEntity.class);
            List<RecordListEntity> recordList = jsonForAppAnalysisEntity.getRecordList();
            String userID = jsonForAppAnalysisEntity.getDeviceToken();
            List<AppInstallDB> appInstallDBList = new ArrayList<>();
            for (RecordListEntity record : recordList) {
                String appName = record.getAppName();
                String packageName = record.getPackageName();

                Integer categoryID = this.queryCategoryByAppName(appName);
                AppInstallDB installDB = new AppInstallDB();
                installDB.setAppName(appName);
                installDB.setPacageName(packageName);
                installDB.setAppCategoryID(categoryID);
                installDB.setUserID(userID);
                appInstallDBList.add(installDB);
            }

            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(appInstallDBList,AppInstallDB.ENTITY_NAME);
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.receiveInstallApp(String json)",e);
            throw new RuntimeException("获取app安装信息出错");
        }
    }


    private static int noticeLogCount = 0;

    /**
     * 保存客户端的通知
     * @param json
     */
    public void saveNoticeFromClientJson(String json)
    {

        try
        {
            Gson gson = new Gson();
            List<NoticeFromClientDB> noticeList = gson.fromJson(json, new TypeToken<ArrayList<NoticeFromClientDB>>() {
            }.getType());
            for (int i = 0; i < noticeList.size(); i++) {
                NoticeFromClientDB noticeFromClientDB =  noticeList.get(i);
                Integer categoryID = this.queryCategoryByAppName(noticeFromClientDB.getAppName());
                noticeFromClientDB.setCategoryID(categoryID);
            }
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            mongoTemplate.insert(noticeList,NoticeFromClientDB.ENTITY_NAME);

        } catch (Exception e) {
            if (noticeLogCount < 3) {
                LOGGER.error("出错的json为:"+json);
                LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.saveNoticeFromClientJson(String json)",e);
                noticeLogCount++;
            }
            throw new RuntimeException("保存客户端的通知出错");
        }
    }






    /**
     * 查询客户端的通知
     * @param pager
     * @param condition
     * @return
     */
    public List<NoticeFromClientPojo> queryClientNoticeByCondition(Pager pager,NoticeFromClientPojo condition)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = Criteria.where("");
            String appName = condition.getAppName();
            if (appName != null)
            {
                appName = appName.trim();
                if (!"".equals(appName))
                {
                    criteria = criteria.and("appName").regex(appName);
                }
            }

            List<Integer> categoriesCondtion = condition.getCategoriesCondtion();
            if (categoriesCondtion != null && !categoriesCondtion.isEmpty())
            {
                criteria = criteria.and("categoryID").in(categoriesCondtion);
            }

            List<Criteria> timeCondition = new ArrayList<Criteria>();
            String beginDateString = condition.getBeginDateCondition();
            if (beginDateString != null)
            {
                Long beginDate = DateUtils.stringToDate(beginDateString, "yyyy-MM-dd").getTime();
                timeCondition.add(Criteria.where("createTime").gte(beginDate));
            }

            String endDateString = condition.getEndDateCondition();
            if (endDateString != null)
            {
                Long endDate = DateUtils.roundDate(endDateString, "yyyy-MM-dd").getTimeInMillis();
                timeCondition.add(Criteria.where("createTime").lte(endDate));
            }

            Criteria[] createTimeCondtions = new Criteria[timeCondition.size()];

            for (int i = 0; i < timeCondition.size(); i++) {
                createTimeCondtions[i] =  timeCondition.get(i);
            }

            if (createTimeCondtions.length != 0)
            {
                criteria = criteria.andOperator(createTimeCondtions);
            }
            query.addCriteria(criteria);

            query.with(new Sort(Sort.Direction.DESC,"createTime"));
            if (pager != null) {
                query.skip(pager.getOffset());
                query.limit(pager.getCapacity());
            }

            List<NoticeFromClientPojo> resultList = mongoTemplate.find(query,NoticeFromClientPojo.class,NoticeFromClientPojo.ENTITY_NAME);

            return resultList;
        } catch (Exception e) {
            LOGGER.error("com.szqd.project.common.service.PushMessageServiceImpl.queryClientNoticeByCondition(Pager pager,NoticeFromClientPojo condition)",e);
            throw new RuntimeException("com.szqd.project.common.service.PushMessageServiceImpl.queryClientNoticeByCondition(Pager pager,NoticeFromClientPojo condition)");
        }
    }


    public void testSave(Object push,String key)
    {
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(key);
        boundValueOps.set(push);
    }

    public Object testGet(String key)
    {
        RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
        BoundValueOperations boundValueOps = redisTemplate.boundValueOps(key);
        Object value = boundValueOps.get();

        PushMessageDB list = (PushMessageDB)new BeanUtil<PushMessageDB>().convertMapToBean(value, PushMessageDB.class);
//        List<PushMessageDB> list = (List<PushMessageDB>)new BeanUtil<PushMessageDB>().convertOneMapListToOneBean(value,PushMessageDB.class);

        return list;
    }

}
