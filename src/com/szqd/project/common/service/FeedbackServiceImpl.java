package com.szqd.project.common.service;

import com.szqd.framework.model.Pager;
import com.szqd.framework.model.SequenceEntity;
import com.szqd.framework.service.SuperService;
import com.szqd.project.common.model.FeedbackEntity;
import com.szqd.project.common.model.FeedbackPojo;
import com.szqd.project.common.model.UninstallCountEntity;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by like on 4/20/15.
 */
public class FeedbackServiceImpl extends SuperService implements FeedbackService{

    private static final Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
    private FeedbackService feedbackService = null;

    /**
     * 保存反馈信息
     * @param feedback
     */
    public void saveFeedback(FeedbackEntity feedback)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            feedback.setCreateTime(Calendar.getInstance().getTimeInMillis());
            mongoTemplate.insert(feedback, FeedbackEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.FeedbackServiceImpl.saveFeedback(FeedbackEntity feedback)",e);
            throw new RuntimeException("保存反馈信息出错");
        }
    }

    /**
     * 分页查找反馈信息
     * @param pager
     * @param queryCondition
     * @param beginDateString
     * @param endDateString
     * @return
     */
    public List<FeedbackPojo> findFeedbackListByPage(Pager pager,FeedbackEntity queryCondition,String beginDateString,String endDateString)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Query query = new Query();
            Criteria criteria = null;
            if (queryCondition != null)
            {
                String appName = queryCondition.getAppName();
                if (appName != null)
                {
                    criteria = Criteria.where("appName").regex(appName.trim());
                }

                String phoneModel = queryCondition.getPhoneModel();
                if (phoneModel != null)
                {
                    if (criteria == null)
                    {
                        criteria = Criteria.where("phoneModel").regex(phoneModel.trim());
                    } else
                    {
                        criteria.and("phoneModel").regex(phoneModel.trim());
                    }
                }

                Integer platform = queryCondition.getPlatform();
                if (platform != null)
                {
                    if (criteria == null)
                    {
                        criteria = Criteria.where("platform").is(platform);
                    }
                    else {
                        criteria.and("platform").is(platform);
                    }
                }

                List<Criteria> timeCondition = new ArrayList<Criteria>();
                if (beginDateString != null)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    Long beginDate = sf.parse(beginDateString).getTime();
                    timeCondition.add(Criteria.where("createTime").gte(beginDate));
                }
                if (endDateString != null)
                {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    Long endDate = sf.parse(endDateString).getTime();
                    timeCondition.add(Criteria.where("createTime").lte(endDate));
                }
                if (timeCondition.size() != 0)
                {
                    Criteria[] criteriasForDate = new Criteria[timeCondition.size()];
                    for (int i = 0 ; i < timeCondition.size(); i++)
                    {
                        criteriasForDate[i] = timeCondition.get(i);
                    }
                    if (criteriasForDate.length > 0)
                    {
                        if (criteria == null)
                        {
                            criteria = criteria.where("").andOperator(criteriasForDate);
                        }
                        else
                        {
                            criteria.andOperator(criteriasForDate);
                        }
                    }
                }

                if (criteria != null)
                {
                    query.addCriteria(criteria);
                }

            }

            if (pager != null)
            {
                query.skip(pager.getOffset());
                query.limit(pager.getCapacity());
            }
            Sort sort = new Sort(Sort.Direction.DESC,"createTime");
            query.with(sort);
            List<FeedbackPojo> list = mongoTemplate.find(query,FeedbackPojo.class,FeedbackEntity.ENTITY_NAME);
            return list;
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.FeedbackServiceImpl.findFeedbackListByPage(Pager pager,FeedbackEntity queryCondition)",e);
            throw new RuntimeException("分页查找反馈信息出错");
        }
    }

//    /**
//     * 添加卸载次数
//     * @param appName
//     */
//    public void addUninstallTotalCount(String appName)
//    {
//        try {
//            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
//            Criteria criteria = Criteria.where("appName").is(appName);
//            Query query = new Query(criteria);
//            Update update = new Update();
//            update.inc("count",1);
//            FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
//            findAndModifyOptions.returnNew(true);
//            findAndModifyOptions.upsert(true);
//            UninstallCountEntity uninstallCountEntity = mongoTemplate.findAndModify(query, update,findAndModifyOptions,UninstallCountEntity.class,UninstallCountEntity.ENTITY_NAME);
//        } catch (Exception e) {
//            logger.error("com.szqd.project.common.service.FeedbackServiceImpl.addUninstallTotalCount(String appName)",e);
//            throw new RuntimeException("添加卸载次数出错");
//        }
//    }

    /**
     * 添加每天的卸载次数
     * @param appName
     */
    public void addUninstallTodayCount(String appName)
    {
        try {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();

            Calendar now = Calendar.getInstance();
            now.set(Calendar.HOUR,0);
            now.set(Calendar.HOUR_OF_DAY,0);
            now.set(Calendar.MINUTE,0);
            now.set(Calendar.SECOND,0);
            now.set(Calendar.MILLISECOND,0);

            Criteria criteria = Criteria.where("appName").is(appName).and("date").is(now.getTimeInMillis());
            Query query = new Query(criteria);
            Update update = new Update();
            update.inc("count",1);
            FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions();
            findAndModifyOptions.returnNew(true);
            findAndModifyOptions.upsert(true);
            UninstallCountEntity uninstallCountEntity = mongoTemplate.findAndModify(query, update,findAndModifyOptions,UninstallCountEntity.class,UninstallCountEntity.ENTITY_NAME);
        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.FeedbackServiceImpl.addUninstallTodayCount(String appName)",e);
            throw new RuntimeException("添加每天的卸载次数出错");
        }
    }

    /**
     * 获取卸载信息
     * @param appName
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<Map> fetchUninstall(String appName,Long beginDate,Long endDate)
    {
        try
        {
            MongoTemplate mongoTemplate = this.getCoreDao().getMongoTemplate();
            Criteria criteria = Criteria.where("");
            Criteria beginDateCriteria = new Criteria(),
                    endDateCriteria = new Criteria();

            if (appName != null && !appName.trim().equals(""))
            {
                criteria.and("appName").regex(appName);
            }
            if (beginDate != null)
            {
                beginDateCriteria = Criteria.where("date").gte(beginDate);
            }
            if (endDate != null)
            {
                endDateCriteria = Criteria.where("date").lte(endDate);
            }
            criteria = criteria.andOperator(beginDateCriteria,endDateCriteria);

            MatchOperation matchOperation = Aggregation.match(criteria);
            ProjectionOperation projectionOperation = Aggregation.project("_id","uninstallCount");
            GroupOperation groupOperation = Aggregation.group("appName").sum("count").as("uninstallCount");

            Aggregation aggregation = Aggregation.newAggregation(matchOperation,groupOperation,projectionOperation);
            List<Map> resultList = mongoTemplate.aggregate(aggregation,UninstallCountEntity.ENTITY_NAME,Map.class).getMappedResults();
            return resultList;

        } catch (Exception e) {
            logger.error("com.szqd.project.common.service.FeedbackServiceImpl.fetchUninstall(String appName,Long beginDate,Long endDate)",e);
            throw new RuntimeException("获取卸载信息出错");
        }
    }



    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
}
