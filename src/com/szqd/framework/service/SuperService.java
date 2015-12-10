package com.szqd.framework.service;


import com.szqd.framework.model.SequenceEntity;
import com.szqd.framework.persistence.CoreDao;
import com.szqd.project.mobile.lock.model.URLCategoryEntity;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Created by mac on 14-5-27.
 */
public class SuperService {

    private static final Logger LOGGER = Logger.getLogger(SuperService.class);

    private CoreDao coreDao = null;

    public CoreDao getCoreDao() {
        return coreDao;
    }

    public void setCoreDao(CoreDao coreDao) {
        this.coreDao = coreDao;
    }

    /**
     * 根据fieldKey获取自增列的新值
     * @param fieldKey
     * @return
     */
    public long getNextSequenceForFieldKey(String fieldKey)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Criteria criteria = Criteria.where("_id").is(fieldKey);
            Query query = new Query(criteria);
            Update update = new Update();
            update.inc("seq",1l);
            FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
            findAndModifyOptions.returnNew(true);
            findAndModifyOptions.upsert(true);
            SequenceEntity sequence = mongoTemplate.findAndModify(query, update,findAndModifyOptions,SequenceEntity.class,SequenceEntity.ENTITY_NAME);
            return sequence.getSeq();
        } catch (Exception e) {
            LOGGER.error("com.szqd.framework.service.SuperService.getNextSequenceForFieldKey(String fieldKey)",e);
            throw new RuntimeException("根据fieldKey获取自增列的新值出错");
        }

    }

    public String generateURIForForward(String action,String beanID,String key)
    {
        StringBuilder uriBuilder = new StringBuilder();
        uriBuilder.append("/common").append("/forward-url").append("/").append(action).append("/").append(beanID).append("/").append(key).append(".do");
        return uriBuilder.toString();
    }


    private static final String CACHE_KEY_FOR_GET_CATEGORY_ID_FROM_APP_NAME = "CACHE_KEY_FOR_GET_CATEGORY_ID_FROM_APP_NAME";
    /**
     * 根据app名称查询app分类
     * @param appName
     * @return
     */
    public Integer queryCategoryByAppName(String appName)
    {
        try
        {
            if (appName != null)
            {
                appName = appName.trim();
            }
            RedisTemplate<String,Integer> redisTemplate = this.getCoreDao().getRedisTemplateWithJsonSerializer();
            BoundHashOperations<String,String,Integer> boundHashOps = redisTemplate.boundHashOps(CACHE_KEY_FOR_GET_CATEGORY_ID_FROM_APP_NAME);
            Integer categoryID = boundHashOps.get(appName);

            if (categoryID == null)
            {
                MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
                String queryString = "{appName:\"%s\"}";
                queryString = String.format(queryString, appName);
                String fields = "{_id:0,categoryID:1}";
                BasicQuery query = new BasicQuery(queryString,fields);
                URLCategoryEntity category = mongoTemplate.findOne(query, URLCategoryEntity.class, URLCategoryEntity.ENTITY_NAME);
                if (category == null)
                {
                    categoryID = -1;
                }
                else{
                    categoryID = category.getCategoryID();
                }
                boundHashOps.put(appName,categoryID);
                LOGGER.debug("数据库:根据app名称通过数据库查询到app的分类id");
                return categoryID;

            }
            else{
                LOGGER.debug("缓存:根据app名称查询到app的分类id");
                return categoryID;
            }
        } catch (Exception e) {
            LOGGER.error("com.szqd.framework.service.SuperService.queryCategoryByAppName(String appName)",e);
            throw new RuntimeException("通过app名称查询分类出错");
        }
    }


}
