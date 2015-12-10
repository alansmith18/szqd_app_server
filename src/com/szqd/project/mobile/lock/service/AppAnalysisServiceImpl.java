package com.szqd.project.mobile.lock.service;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.szqd.framework.model.SelectEntity;
import com.szqd.framework.service.SuperService;
import com.szqd.project.mobile.lock.model.AppAnalysisEntity;
import com.szqd.project.mobile.lock.model.HowLongUseEnum;
import com.szqd.project.mobile.lock.model.URLCategoryEntity;
import com.szqd.project.mobile.lock.model.json.JSONForAppAnalysisEntity;
import com.szqd.project.mobile.lock.model.json.RecordListEntity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by like on 3/24/15.
 */
public class AppAnalysisServiceImpl extends SuperService implements AppAnalysisService {

    private static Logger logger = Logger.getLogger(AppAnalysisServiceImpl.class);

    /**
     * 将客户端的json数据解析并且放入到缓存任务队列中
     * @param clientJson
     */
    public void saveAppFromClientJson(String clientJson)
    {
        JSONForAppAnalysisEntity jsonObject = this.parseJson(clientJson);

        this.saveAppUseageInfoToCache(jsonObject);
    }

    private static final String ERROR_FOR_PARSE_JSON = "解析json数据出错,出错的json字符串为: %s";

    /**
     * 解析json数据到对象
     * @param json
     * @return
     */
    public JSONForAppAnalysisEntity parseJson(String json)
    {
        try {

            Gson gson = new Gson();
            JSONForAppAnalysisEntity jsonForAppAnalysisEntity = gson.fromJson(json,JSONForAppAnalysisEntity.class);

            return jsonForAppAnalysisEntity;

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.saveAppFromClientJson(String clientJson)",e);
            String errorInfo = String.format(ERROR_FOR_PARSE_JSON,json);
            throw new RuntimeException(errorInfo);
        }
    }



    private static final String DEVICE_KEY = "%s:%s:%s";//format = deviceID:appID:randomNumber

    private static final String FIELD_APP_NAME = "appName";
    private static final String FIELD_HOW_LONG_USE = "howLongUse";
    private static final String FIELD_LAUCH_TIME = "lauchTime";
    private static final String FIELD_EXIT_TIME = "exitTime";
    private static final String FIELD_APP_ID = "appID";

    private static final String QUEUE_FOR_INSERT_TO_DATABASE = "waitForInsertAnalysisToDatabase";
    /**
     * 保存app单次记录的实体
     * @param jsonObject
     */
    public void saveAppUseageInfoToCache(JSONForAppAnalysisEntity jsonObject)
    {
        try
        {
            RedisTemplate redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();

            String deviceID = jsonObject.getDeviceToken();
            List<RecordListEntity> recordList = jsonObject.getRecordList();

            for (int i = 0 ; i < recordList.size(); i++)
            {
                RecordListEntity record = recordList.get(i);
                String appName = record.getAppName().trim();

                Long exitTime = record.getExitTimeForLongType();
                Long lauchTime = record.getLauchTimeForLongType();
                String packageName = record.getPackageName().trim();
                Long howLongUse = Math.abs(exitTime - lauchTime);
                boolean isInCorrect = exitTime == null || lauchTime == null || packageName == null || "".equals(packageName) || appName == null || "".equals(appName);
                if (isInCorrect) continue;

                String deviceKeyUUID = UUID.randomUUID().toString();
                String deviceKey = String.format(DEVICE_KEY,deviceID,packageName,deviceKeyUUID);

                boolean isDeviceKeyExists = redisTemplate.hasKey(deviceKey);
                if (isDeviceKeyExists)
                {
                    logger.error("deviceKey重复:"+deviceKey);
                    continue;
                }

//                redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
                BoundHashOperations appHashOperations = redisTemplate.boundHashOps(deviceKey);
                Map<String,Object> keyAndValue = new HashMap<String, Object>();
                keyAndValue.put(FIELD_APP_NAME,appName);
                keyAndValue.put(FIELD_HOW_LONG_USE,howLongUse);
                keyAndValue.put(FIELD_LAUCH_TIME,lauchTime);
                keyAndValue.put(FIELD_EXIT_TIME,exitTime);
                appHashOperations.putAll(keyAndValue);
                //设置过期时间确保出错移除
                redisTemplate.expire(deviceKey,1, TimeUnit.DAYS);
                logger.debug("#########将数据缓存到redis");

                BoundListOperations queueForInsert = redisTemplate.boundListOps(QUEUE_FOR_INSERT_TO_DATABASE);
                queueForInsert.leftPush(deviceKey);
                logger.debug("#########将任务保存到队列");
            }
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.saveAppUseageInfoToCache(List<AppAnalysisEntity> appAnalysisEntityList)",e);
            throw new RuntimeException("保存基础数据到redis缓存中出错");
        }
        finally {
            jsonObject.setRecordList(null);
            jsonObject.setDeviceToken(null);
            jsonObject = null;
        }
    }

    private static final String ERROR_INVALID_KEY = "在队列中出现无效的key, key: %s";
    /**
     * 执行redis队列中得任务,将基础数据保存到数据库中
     */
    public void saveAppUseAgeInfoToDatabase()
    {

        String deviceKey = null;
        BoundListOperations<String,String> queue = null;
        RedisTemplate redisTemplate = null;
        try
        {
            redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            queue = redisTemplate.boundListOps(QUEUE_FOR_INSERT_TO_DATABASE);
//            if (queue.size() != 0) {
//                System.out.println(queue.size());
//            }
            //每秒循环取100个
            for (int i = 0; i < 50 ; i++)
            {

                deviceKey = queue.rightPop();

                if (deviceKey == null) {
//                    logger.info("############redis队列中没有要执行的任务");
                    return;
                }

                if (!redisTemplate.hasKey(deviceKey))
                {
                    logger.error(String.format(ERROR_INVALID_KEY,deviceKey));
                }



                BoundHashOperations<String, String, Object> deviceHash = redisTemplate.boundHashOps(deviceKey);
                String appName = (String) deviceHash.get(FIELD_APP_NAME);
                Long howLongUse = null;
                Long lauchTime = null;
                Long exitTime = null;
                try {
                    howLongUse = Long.valueOf((String)deviceHash.get(FIELD_HOW_LONG_USE));
                    lauchTime = Long.valueOf((String) deviceHash.get(FIELD_LAUCH_TIME));
                    exitTime = Long.valueOf((String) deviceHash.get(FIELD_EXIT_TIME));
                } catch (Exception e) {

                }
                String[] deviceKeyArray = deviceKey.split(":");
                String deviceID = deviceKeyArray[0];
                String appID = deviceKeyArray[1];

                AppAnalysisEntity appAnalysisEntity = new AppAnalysisEntity();
                appAnalysisEntity.setAppName(appName);
                appAnalysisEntity.setHowLongUse(howLongUse);
                appAnalysisEntity.setLauchTime(lauchTime);
                appAnalysisEntity.setExitTime(exitTime);
                appAnalysisEntity.setAppID(appID);
                appAnalysisEntity.setUserID(deviceID);



                if (lauchTime != null)
                {
                    Calendar lauchTimeDate = Calendar.getInstance();
                    lauchTimeDate.setTimeInMillis(lauchTime);
                    appAnalysisEntity.setLauchTimeHours(lauchTimeDate.get(Calendar.HOUR_OF_DAY));
                }

                Integer howLongUseCategory = appAnalysisEntity.getHowLongUseCategoryByHowLongUse();
                appAnalysisEntity.setHowLongUseCategory(howLongUseCategory);

                Integer categoryID = this.queryCategoryByAppName(appName);
                appAnalysisEntity.setAppCategoryID(categoryID);

                this.getCoreDao().getMongoTemplate().insert(appAnalysisEntity, AppAnalysisEntity.ENTITY_NAME);

                logger.debug("#########插入mongodb任务已经成功执行");

                redisTemplate.delete(deviceKey);
                logger.debug("#########将任务从redis队列删除");
            }
        }
        catch (Exception e)
        {

            logger.error("异常:com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.saveAppUseAgeInfoToDatabase()",e);


//            if (e instanceof NullPointerException)
//            {
//                redisTemplate.delete(deviceKey);
//                logger.debug("###########由于缓存数据为空导致执行出错, 任务将删除,deviceKey: "+deviceKey);
//            }
//            else
//            {
//
//                if(queue != null && deviceKey != null)
//                {
//                    queue.leftPush(deviceKey);
//                    logger.debug("###########由于任务执行出错, 任务将返还回队列,deviceKey: "+deviceKey);
//                }
//            }
//            throw new RuntimeException("执行异步保存队列任务到数据库出错");
        }
    }

    public Integer queryCategoryByAppName(String appName)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            String queryString = "{appName:\"%s\"}";
            queryString = String.format(queryString, appName);
            String fields = "{_id:0,categoryID:1}";
            BasicQuery query = new BasicQuery(queryString,fields);
            URLCategoryEntity category = mongoTemplate.findOne(query, URLCategoryEntity.class, URLCategoryEntity.ENTITY_NAME);
            if (category != null)
            {
                return category.getCategoryID();
            }
            return -1;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.queryCategoryByAppName(String appName)",e);
            throw new RuntimeException("通过app名称查询分类");
        }
    }

    /**
     * 查询用户分析中的app列表
     * @return
     */
    public List<SelectEntity> queryAppListWithAnalysis()
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            GroupOperation groupOperation = Aggregation.group("appID").first("appID").as("value").first("appName").as("text");
            ProjectionOperation projectionOperation = Aggregation.project("text", "value");
            List<AggregationOperation> aggregationOperations = Arrays.asList(groupOperation,projectionOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            AggregationResults<SelectEntity> aggregationResults  = mongoTemplate.aggregate(aggregation,AppAnalysisEntity.ENTITY_NAME,SelectEntity.class);
            List<SelectEntity> resultList = aggregationResults.getMappedResults();
            return resultList;

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.queryAppListWithAnalysis()",e);
            throw new RuntimeException("查询用户分析中的app列表出错");
        }
    }

    /**
     * 根据单个app的id统计时长使用情况
     * @param appID
     * @return
     */
    public List<HashMap> countTimeUseageByAppID(String appID,Integer categoryID)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            Criteria criteria = null;
            if (appID != null)
            {
                criteria = Criteria.where("appID").is(appID);
            }
            else if (categoryID != null)
            {
                criteria = Criteria.where("appCategoryID").is(categoryID);
            }
            else {
                return null;
            }

            MatchOperation matchOperation = new MatchOperation(criteria);
            GroupOperation groupOperation = Aggregation.group("howLongUseCategory").count().as("useCategoryCount");
            ProjectionOperation projectionOperation = Aggregation.project("_id", "useCategoryCount");
            SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "_id");
            List<AggregationOperation> aggregationOperations = Arrays.asList(matchOperation, groupOperation, projectionOperation, sortOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);


            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation, AppAnalysisEntity.ENTITY_NAME, HashMap.class);
            List<HashMap> resultList = aggregationResults.getMappedResults();
            resultList.forEach((map)->map.computeIfAbsent("categoryName",(key)->HowLongUseEnum.getEnumByID((Integer) map.get("_id"))));
            return resultList;

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.countTimeUseageByAppID(String appID)",e);
            throw new RuntimeException("根据单个app的id统计时长使用情况出错");
        }

    }

    /**
     * 根据app类别查询类别总得使用时长
     * @return
     */
    public List<HashMap> countTotalTimeUseage()
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            GroupOperation groupOperation = Aggregation.group("appCategoryID").sum("howLongUse").as("categoryUseTimeTotalCount");
            ProjectionOperation projectionOperation = Aggregation.project("_id","categoryUseTimeTotalCount");
            SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"categoryUseTimeTotalCount");
            List<AggregationOperation> aggregationOperations = Arrays.asList(groupOperation, projectionOperation,sortOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation, AppAnalysisEntity.ENTITY_NAME, HashMap.class);
            List<HashMap> resultList = aggregationResults.getMappedResults();
            List<URLCategoryEntity> categoryList = this.getCategoryList();

            URLCategoryEntity otherCategory = new URLCategoryEntity();
            otherCategory.setCategoryID(-1);
            otherCategory.setCategoryName("其他类别");
            categoryList.add(otherCategory);

            resultList.forEach((map) -> map.computeIfAbsent("categoryName", (t) -> categoryList.stream().filter(cateVar -> cateVar.getCategoryID().equals((Integer) map.get("_id"))).findFirst().get().getCategoryName()));

            return resultList;
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.countTotalTimeUseageByCategoryID()",e);
            throw new RuntimeException("根据app类别查询类别总得使用时长出错");
        }
    }

    /**
     * 根据单个app统计时段使用情况
     * @param appID
     * @return
     */
    public List<HashMap> countTimeByAppID(String appID,Integer categoryID)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            Criteria criteria = null;
            if (appID != null)
            {
                criteria = Criteria.where("appID").is(appID);
            }
            else if (categoryID != null)
            {
                criteria = Criteria.where("appCategoryID").is(categoryID);
            }
            else {
                return null;
            }

            MatchOperation matchOperation = new MatchOperation(criteria);

            GroupOperation groupOperation = Aggregation.group("lauchTimeHours").count().as("lauchTimeHoursCount");

            ProjectionOperation projectionOperation = Aggregation.project("_id", "lauchTimeHoursCount");

            SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "_id");

            List<AggregationOperation> aggregationOperations = Arrays.asList(matchOperation, groupOperation, projectionOperation,sortOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation,AppAnalysisEntity.ENTITY_NAME,HashMap.class);
            List<HashMap> resultList = aggregationResults.getMappedResults();
            return resultList;

        }
        catch (Exception e)
        {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.countTimeByAppID(String appID)",e);
            throw new RuntimeException("根据单个app统计时段使用情况出错");
        }

    }



    /**
     * 根据单个app统计用户使用次数
     * @param appID
     * @param countMin
     * @param countMax
     * @return
     */
    public HashMap countFrequencyByAppID(String appID,Integer categoryID, Integer countMin,Integer countMax)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            Criteria criteria = null;
            if (appID != null)
            {
                criteria = Criteria.where("appID").is(appID);
            }
            else if (categoryID != null)
            {
                criteria = Criteria.where("appCategoryID").is(categoryID);
            }
            else {
                return null;
            }

            MatchOperation matchOperation = new MatchOperation(criteria);

            GroupOperation groupOperation = Aggregation.group("userID").count().as("userCount");
            ProjectionOperation projectionOperation = Aggregation.project("_id","userCount");

            Criteria havingCriteria = Criteria.where("userCount").gte(countMin);
            if (countMax != null)
            {
                havingCriteria.lte(countMax);
            }
            MatchOperation matchHavingOperation = new MatchOperation(havingCriteria);
            GroupOperation allUserCountOperation = Aggregation.group().count().as("allUserCount");

            List<AggregationOperation> aggregationOperations = Arrays.asList(matchOperation, groupOperation, projectionOperation, matchHavingOperation, allUserCountOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

            AggregationResults<HashMap> aggregationResults = mongoTemplate.aggregate(aggregation,AppAnalysisEntity.ENTITY_NAME,HashMap.class);
            HashMap result = aggregationResults.getUniqueMappedResult();
            return result;
        }
        catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.countFrequencyByAppID(String appID, Integer countMin,Integer countMax)",e);
            throw new RuntimeException("根据单个app统计用户使用次数出错");
        }
    }



    /**
     * 通过用户id查找兴趣(app分类)
     * @param userID
     * @return
     */
    public Integer queryInterestByUserID(String userID)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            MatchOperation matchOperation = new MatchOperation(Criteria.where("userID").is(userID).and("appCategoryID").nin(-1));
            GroupOperation groupOperation = Aggregation.group("appCategoryID").count().as("categoryCount");
            SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "categoryCount");
            LimitOperation limitOperation = Aggregation.limit(1);
            ProjectionOperation projectionOperation = Aggregation.project("_id");
            List<AggregationOperation> aggregationOperations = Arrays.asList(matchOperation, groupOperation, sortOperation, projectionOperation, limitOperation);
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            Map<String,Integer> appCategoryID = mongoTemplate.aggregate(aggregation, AppAnalysisEntity.ENTITY_NAME, HashMap.class).getUniqueMappedResult();
            if (appCategoryID != null)
            {
                return appCategoryID.get("_id");
            }
            return null;

        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.queryInterestByUserID(Integer userID)",e);
            throw new RuntimeException("通过用户id查找兴趣出错");
        }
    }


    public List<URLCategoryEntity> getCategoryList()
    {
        URLCategoryEntity uc1 = new URLCategoryEntity();
        uc1.setCategoryID(1);
        uc1.setCategoryName("系统安全");
        uc1.setUrl("http://zhushou.360.cn/list/index/cid/11/?page=%s");
        uc1.setPageCount(50);

        URLCategoryEntity uc2 = new URLCategoryEntity();
        uc2.setCategoryID(2);
        uc2.setCategoryName("通讯社交");
        uc2.setUrl("http://zhushou.360.cn/list/index/cid/12/?page=%s");
        uc2.setPageCount(50);

        URLCategoryEntity uc3 = new URLCategoryEntity();
        uc3.setCategoryID(3);
        uc3.setCategoryName("影音视听");
        uc3.setUrl("http://zhushou.360.cn/list/index/cid/14/?page=%s");
        uc3.setPageCount(50);

        URLCategoryEntity uc4 = new URLCategoryEntity();
        uc4.setCategoryID(4);
        uc4.setCategoryName("新闻阅读");
        uc4.setUrl("http://zhushou.360.cn/list/index/cid/15/?page=%s");
        uc4.setPageCount(50);

        URLCategoryEntity uc5 = new URLCategoryEntity();
        uc5.setCategoryID(5);
        uc5.setUrl("http://zhushou.360.cn/list/index/cid/16/?page=%s");
        uc5.setCategoryName("生活休闲");
        uc5.setPageCount(50);

        URLCategoryEntity uc6 = new URLCategoryEntity();
        uc6.setCategoryID(6);
        uc6.setUrl("http://zhushou.360.cn/list/index/cid/18/?page=%s");
        uc6.setCategoryName("主题壁纸");
        uc6.setPageCount(50);

        URLCategoryEntity uc7 = new URLCategoryEntity();
        uc7.setCategoryID(7);
        uc7.setUrl("http://zhushou.360.cn/list/index/cid/17/?page=%s");
        uc7.setCategoryName("办公商务");
        uc7.setPageCount(50);

        URLCategoryEntity uc8 = new URLCategoryEntity();
        uc8.setCategoryID(8);
        uc8.setUrl("http://zhushou.360.cn/list/index/cid/102228/?page=%s");
        uc8.setCategoryName("摄影摄像");
        uc8.setPageCount(42);

        URLCategoryEntity uc9 = new URLCategoryEntity();
        uc9.setCategoryID(9);
        uc9.setUrl("http://zhushou.360.cn/list/index/cid/102230/?page=%s");
        uc9.setCategoryName("购物优惠");
        uc9.setPageCount(50);

        URLCategoryEntity uc10 = new URLCategoryEntity();
        uc10.setCategoryID(10);
        uc10.setUrl("http://zhushou.360.cn/list/index/cid/102231/?page=%s");
        uc10.setCategoryName("地图旅游");
        uc10.setPageCount(50);

        URLCategoryEntity uc11 = new URLCategoryEntity();
        uc11.setCategoryID(11);
        uc11.setUrl("http://zhushou.360.cn/list/index/cid/102232/?page=%s");
        uc11.setCategoryName("教育学习");
        uc11.setPageCount(50);

        URLCategoryEntity uc12 = new URLCategoryEntity();
        uc12.setCategoryID(12);
        uc12.setUrl("http://zhushou.360.cn/list/index/cid/102139/?page=%s");
        uc12.setCategoryName("金融理财");
        uc12.setPageCount(50);

        URLCategoryEntity uc13 = new URLCategoryEntity();
        uc13.setCategoryID(13);
        uc13.setUrl("http://zhushou.360.cn/list/index/cid/102233/?page=%s");
        uc13.setCategoryName("健康医疗");
        uc13.setPageCount(50);

        URLCategoryEntity uc14 = new URLCategoryEntity();
        uc14.setCategoryID(14);
        uc14.setUrl("http://zhushou.360.cn/list/index/cid/101587/?page=%s");
        uc14.setCategoryName("角色扮演");
        uc14.setPageCount(50);

        URLCategoryEntity uc15 = new URLCategoryEntity();
        uc15.setCategoryID(15);
        uc15.setUrl("http://zhushou.360.cn/list/index/cid/19/?page=%s");
        uc15.setCategoryName("休闲益智");
        uc15.setPageCount(50);

        URLCategoryEntity uc16 = new URLCategoryEntity();
        uc16.setCategoryID(16);
        uc16.setUrl("http://zhushou.360.cn/list/index/cid/20/?page=%s");
        uc16.setCategoryName("动作冒险");
        uc16.setPageCount(50);

        URLCategoryEntity uc17 = new URLCategoryEntity();
        uc17.setCategoryID(17);
        uc17.setUrl("http://zhushou.360.cn/list/index/cid/100451/?page=%s");
        uc17.setCategoryName("网络游戏");
        uc17.setPageCount(16);

        URLCategoryEntity uc18 = new URLCategoryEntity();
        uc18.setCategoryID(18);
        uc18.setUrl("http://zhushou.360.cn/list/index/cid/51/?page=%s");
        uc18.setCategoryName("体育竞技");
        uc18.setPageCount(50);

        URLCategoryEntity uc19 = new URLCategoryEntity();
        uc19.setCategoryID(19);
        uc19.setUrl("http://zhushou.360.cn/list/index/cid/52/?page=%s");
        uc19.setCategoryName("飞行射击");
        uc19.setPageCount(50);

        URLCategoryEntity uc20 = new URLCategoryEntity();
        uc20.setCategoryID(20);
        uc20.setUrl("http://zhushou.360.cn/list/index/cid/53/?page=%s");
        uc20.setCategoryName("经营策略");
        uc20.setPageCount(50);

        URLCategoryEntity uc21 = new URLCategoryEntity();
        uc21.setCategoryID(21);
        uc21.setUrl("http://zhushou.360.cn/list/index/cid/54/?page=%s");
        uc21.setCategoryName("棋牌天地");
        uc21.setPageCount(50);

        URLCategoryEntity uc22 = new URLCategoryEntity();
        uc22.setCategoryID(22);
        uc22.setUrl("http://zhushou.360.cn/list/index/cid/102238/?page=%s");
        uc22.setCategoryName("儿童游戏");
        uc22.setPageCount(3);

        List<URLCategoryEntity> list = new ArrayList<>();
        list.add(uc1);
        list.add(uc2);
        list.add(uc3);
        list.add(uc4);
        list.add(uc5);
        list.add(uc6);
        list.add(uc7);
        list.add(uc8);
        list.add(uc9);
        list.add(uc10);
        list.add(uc11);
        list.add(uc12);
        list.add(uc13);
        list.add(uc14);
        list.add(uc15);
        list.add(uc16);
        list.add(uc17);
        list.add(uc18);
        list.add(uc19);
        list.add(uc20);
        list.add(uc21);
        list.add(uc22);


        return list;
    }


    public void saveAppCategoryFrom360()
    {

        List<URLCategoryEntity> list = this.getCategoryList();
        addAllCategory(list);
    }

    private void addAllCategory(List<URLCategoryEntity> list)
    {
        for(URLCategoryEntity uc : list)
        {
            for (int i = 1; i <= uc.getPageCount(); i++)
            {
                while (true)
                {
                    try
                    {
                        addCategoryToDb(uc,i);
                        break;
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }
    }

    private void addCategoryToDb(URLCategoryEntity uc,int page)
    {

        String url = String.format(uc.getUrl(),page);

        String html = getUrlResult("GET",url,new HashMap<>());

        try
        {
            NodeFilter ulNodefileter = new NodeFilter() {
                @Override
                public boolean accept(Node node) {
                    if (node instanceof LinkTag)
                    {
                        LinkTag link = (LinkTag)node;
                        Node parentNode = link.getParent().getParent().getParent();
                        if (parentNode instanceof BulletList)
                        {
                            BulletList ul = (BulletList)parentNode;
                            String id = ul.getAttribute("id");

                            if ("iconList".equals(id))
                            {
                                return true;
                            }
                        }

                    }
                    return false;

                }
            };

            Parser parser = new Parser();
            parser.setResource(html);
            parser.setEncoding("UTF-8");
            NodeList nodeList = parser.parse(ulNodefileter);
            for (int i = 0; i < nodeList.size(); i++)
            {
                Node liVar = nodeList.elementAt(i);
                String innerText = liVar.toPlainTextString().trim();

                URLCategoryEntity appCategory360 = new URLCategoryEntity();
                appCategory360.setCategoryID(uc.getCategoryID());
                appCategory360.setCategoryName(uc.getCategoryName());
                appCategory360.setAppName(innerText);
                System.out.println(appCategory360.getAppName());
                Long appCategory360ID = this.getNextSequenceForFieldKey(URLCategoryEntity.ENTITY_NAME);
                appCategory360.setId(appCategory360ID);
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                mongoTemplate.insert(appCategory360,URLCategoryEntity.ENTITY_NAME);

            }
        } catch (Exception e) {
            logger.error("com.szqd.project.mobile.lock.service.AppAnalysisServiceImpl.addCategoryToDb(URLCategoryEntity uc,int page)throws Exception",e);
            throw new RuntimeException("添加分类出错");
        }
    }


    private String getUrlResult(String method, String urlString, Map<String, String> paramMap)
    {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDefaultUseCaches(false);
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();


            StringBuilder param = new StringBuilder();
            for (Map.Entry<String, String> en : paramMap.entrySet())
            {
                param.append("&").append(en.getKey() + "=");
                param.append(URLEncoder.encode(en.getValue()));
            }
            if (!param.toString().equals("")) {
                System.out.println(param);
                byte[] data = param.toString().getBytes();
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }


            InputStream inputStream = connection.getInputStream();
            boolean isNotEnd = true;
            byte[] src = new byte[0];
            do
            {
                byte[] returnData = new byte[1024 * 1];
                int readByteCount = inputStream.read(returnData);
                isNotEnd = readByteCount != -1;
                if (isNotEnd)
                {
                    byte[] temp = new byte[readByteCount];
                    System.arraycopy(returnData,0,temp,0,readByteCount);
                    src = ArrayUtils.addAll(src, temp);
                }

            }
            while (isNotEnd);
            connection.disconnect();
            return new String(src,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }

    }


    public void checkNoCategoryApp()
    {
        MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
//        Query query = new Query();
//        query.addCriteria(Criteria.where("appCategoryID").is(-1));
//        query.limit(100);
//        List<AppAnalysisEntity> list = null;
//        int i = 0;
//        do {
//            query.skip(i);
//            list = mongoTemplate.find(query,AppAnalysisEntity.class,AppAnalysisEntity.ENTITY_NAME);
//            System.out.println("查询到list的数量:"+list.size());
//            for (AppAnalysisEntity app : list) {
//                Integer categoryID = this.queryCategoryByAppName(app.getAppName());
//
//                if (categoryID != null && !categoryID.equals(new Integer(-1)))
//                {
//                    System.out.println(app.getAppName()+" change to category: "+categoryID);
//                    Update update = new Update();
//                    update.set("appCategoryID",categoryID);
//                    mongoTemplate.updateFirst(new Query(Criteria.where("appName").is(app.getAppName())),update,AppAnalysisEntity.ENTITY_NAME);
//                }
//
//            }
//            i+=100;
//        } while (list!= null && !list.isEmpty());
        DBObject query = new BasicDBObject();
        query.put("appCategoryID",-1);
        List<String> appNameList = mongoTemplate.getCollection(AppAnalysisEntity.ENTITY_NAME).distinct("appName",query);
        System.out.println("app count: " + appNameList.size());
        for (String appName : appNameList) {
            Integer categoryID = this.queryCategoryByAppName(appName);

            if (categoryID != null && !categoryID.equals(new Integer(-1)))
            {
                System.out.println(appName+" change to category: "+categoryID);
                Update update = new Update();
                update.set("appCategoryID",categoryID);
                mongoTemplate.updateMulti(new Query(Criteria.where("appName").is(appName)),update,AppAnalysisEntity.ENTITY_NAME);
            }
        }

        System.out.println("#######################结束#########################");
    }
}

